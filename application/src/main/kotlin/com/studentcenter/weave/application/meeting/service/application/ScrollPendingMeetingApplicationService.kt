package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.ScrollPendingMeetingUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meeting.vo.PendingMeetingInfo
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetAllByIdsUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamQueryUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ScrollPendingMeetingApplicationService(
    private val meetingDomainService: MeetingDomainService,
    private val meetingTeamQueryUseCase: MeetingTeamQueryUseCase,
    private val meetingTeamGetAllByIdsUseCase: MeetingTeamGetAllByIdsUseCase,
) : ScrollPendingMeetingUseCase {

    @Transactional(readOnly = true)
    override fun invoke(command: ScrollPendingMeetingUseCase.Command): ScrollPendingMeetingUseCase.Result {
        val myTeam = meetingTeamQueryUseCase.findByMemberUserId(getCurrentUserAuthentication().userId)
            ?: throw IllegalArgumentException("내 미팅팀이 존재하지 않아요! 미팅팀에 참여해 주세요!")
        val meetings = meetingDomainService.findAllPendingMeetingByTeamId(
            teamId = myTeam.id,
            isRequester = command.isRequester,
            next = command.next,
            limit = command.limit + 1,
        )
        val next = if(meetings.size > command.limit) meetings.last().id else null
        val items = if (next == null) meetings else meetings.subList(0, command.limit)

        val teamIds = items
            .flatMap { listOf(it.receivingTeamId, it.requestingTeamId) }
            .distinct()
        val meetingTeamAndMemberInfoMap = meetingTeamGetAllByIdsUseCase.invoke(
                command = MeetingTeamGetAllByIdsUseCase.Command(teamIds)
            )
            .teamInfos
            .associateBy { it.team.id }

        val pendingMeetingInfos = items.map {
            val requestingTeamInfo = meetingTeamAndMemberInfoMap[it.requestingTeamId]
                ?: throw IllegalArgumentException()
            val receivingTeamInfo = meetingTeamAndMemberInfoMap[it.receivingTeamId]
                ?: throw IllegalArgumentException()
            PendingMeetingInfo(
                id = it.id,
                requestingTeam = requestingTeamInfo,
                receivingTeam = receivingTeamInfo,
                isRequestingTeam = command.isRequester,
                status = it.status,
                createdAt = it.createdAt,
                pendingEndAt = it.pendingEndAt,
            )
        }


        return ScrollPendingMeetingUseCase.Result(
            items = pendingMeetingInfos,
            next = next,
        )
    }
}
