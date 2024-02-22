package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.ScrollPendingMeetingUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meeting.vo.PendingMeetingInfo
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetAllByIdsUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ScrollPendingMeetingApplicationService(
    private val meetingDomainService: MeetingDomainService,
    private val meetingTeamGetAllByIdsUseCase: MeetingTeamGetAllByIdsUseCase,
) : ScrollPendingMeetingUseCase {

    @Transactional(readOnly = true)
    override fun invoke(command: ScrollPendingMeetingUseCase.Command): ScrollPendingMeetingUseCase.Result {
        val page = meetingDomainService.scrollPendingMeetingByUserId(
            userId = getCurrentUserAuthentication().userId,
            isRequester = command.isRequester,
            next = command.next,
            limit = command.limit,
        )
        val next = if (page.size > command.limit) page.last().id else null

        val meetings = if (next != null) page.subList(0, page.lastIndex) else page
        val teamIds = meetings
            .flatMap { listOf(it.receivingTeamId, it.requestingTeamId) }
            .distinct()
        val meetingTeamAndMemberInfoMap = meetingTeamGetAllByIdsUseCase.invoke(
                command = MeetingTeamGetAllByIdsUseCase.Command(teamIds)
            )
            .teamInfos
            .associateBy { it.team.id }

        val pendingMeetingInfos = meetings.map {
            val requestingTeamInfo = meetingTeamAndMemberInfoMap[it.requestingTeamId]
                ?: throw IllegalArgumentException()
            val receivingTeamInfo = meetingTeamAndMemberInfoMap[it.receivingTeamId]
                ?: throw IllegalArgumentException()
            PendingMeetingInfo(
                id = it.id,
                requesterTeam = requestingTeamInfo,
                receivingTeam = receivingTeamInfo,
                isRequestingTeam = command.isRequester,
                status = it.status,
                createdAt = it.createdAt,
                pendingEndAt = it.createdAt.plusDays(PENDING_DAYS)
            )
        }


        return ScrollPendingMeetingUseCase.Result(
            items = pendingMeetingInfos,
            next = next,
        )
    }

    companion object {
        const val PENDING_DAYS = 3L
    }
}
