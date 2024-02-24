package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.ScrollPendingMeetingUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meeting.vo.PendingMeetingInfo
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamInfoGetAllByIdUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamQueryUseCase
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.enums.TeamType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ScrollPendingMeetingApplicationService(
    private val meetingDomainService: MeetingDomainService,
    private val meetingTeamQueryUseCase: MeetingTeamQueryUseCase,
    private val meetingTeamGetAllByIdsUseCase: MeetingTeamInfoGetAllByIdUseCase,
) : ScrollPendingMeetingUseCase {

    @Transactional(readOnly = true)
    override fun invoke(command: ScrollPendingMeetingUseCase.Command): ScrollPendingMeetingUseCase.Result {
        val myTeam = meetingTeamQueryUseCase.findByMemberUserId(getCurrentUserAuthentication().userId)
            ?: throw IllegalArgumentException("내 미팅팀이 존재하지 않아요! 미팅팀에 참여해 주세요!")

        val (items, next) = scrollByPendingMeetingByTeamId(
            teamId = myTeam.id,
            teamType = command.teamType,
            next = command.next,
            limit = command.limit,
        )

        return ScrollPendingMeetingUseCase.Result(
            items = createPendingMeetingInfos(items, command.teamType),
            next = next,
        )
    }

    private fun scrollByPendingMeetingByTeamId(
        teamId: UUID,
        teamType: TeamType,
        next: UUID?,
        limit: Int,
    ): Pair<List<Meeting>, UUID?> {
        val meetings = meetingDomainService.findAllPendingMeetingByTeamId(
            teamId = teamId,
            teamType = teamType,
            next = next,
            limit = limit + 1,
        )
        val newNext = if (meetings.size > limit) meetings.last().id else null
        val items = if (newNext == null) meetings else meetings.subList(0, limit)
        return items to newNext
    }

    private fun createPendingMeetingInfos(
        items: List<Meeting>,
        teamType: TeamType,
    ) : List<PendingMeetingInfo> {
        val teamIds = getUniqueTeamIds(items)
        val teamIdToTeamInfo = mapTeamIdToTeamInfo(teamIds)

        return items.map {
            val requestingTeamInfo = teamIdToTeamInfo[it.requestingTeamId]
                ?: throw IllegalArgumentException()
            val receivingTeamInfo = teamIdToTeamInfo[it.receivingTeamId]
                ?: throw IllegalArgumentException()
            PendingMeetingInfo(
                id = it.id,
                requestingTeam = requestingTeamInfo,
                receivingTeam = receivingTeamInfo,
                teamType = teamType,
                status = it.status,
                createdAt = it.createdAt,
                pendingEndAt = it.pendingEndAt,
            )
        }
    }

    private fun mapTeamIdToTeamInfo(teamIds: List<UUID>) =
        meetingTeamGetAllByIdsUseCase.invoke(teamIds)
            .associateBy { it.team.id }

    private fun getUniqueTeamIds(items: List<Meeting>) = items
            .flatMap { listOf(it.receivingTeamId, it.requestingTeamId) }
            .distinct()

}
