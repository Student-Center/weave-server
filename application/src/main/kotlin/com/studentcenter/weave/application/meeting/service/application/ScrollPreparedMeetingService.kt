package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.exception.MeetingExceptionType
import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.ScrollPreparedMeeting
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meeting.vo.PreparedMeetingInfo
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetAllMeetingTeamInfo
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ScrollPreparedMeetingService(
    private val meetingDomainService: MeetingDomainService,
    private val getMeetingTeam: GetMeetingTeam,
    private val meetingTeamInfoGetAllByIds: GetAllMeetingTeamInfo,
) : ScrollPreparedMeeting {

    @Transactional(readOnly = true)
    override fun invoke(query: ScrollPreparedMeeting.Query): ScrollPreparedMeeting.Result {
        val myTeam = getMeetingTeam.findByMemberUserId(getCurrentUserAuthentication().userId)
            ?: throw CustomException(
                MeetingExceptionType.NOT_FOUND_MY_MEETING_TEAM,
                "내 미팅팀이 존재하지 않아요! 미팅팀에 참여해 주세요!",
            )

        val (items, next) = scrollByPreparedMeetingByTeamId(
            teamId = myTeam.id,
            next = query.next,
            limit = query.limit,
        )

        return ScrollPreparedMeeting.Result(
            items = createPreparedMeetingInfos(items, myTeam.id),
            next = next,
        )
    }

    private fun scrollByPreparedMeetingByTeamId(
        teamId: UUID,
        next: UUID?,
        limit: Int,
    ): Pair<List<Meeting>, UUID?> {
        val meetings = meetingDomainService.findAllPreparedMeetingByTeamId(
            teamId = teamId,
            next = next,
            limit = limit + 1,
        )
        val newNext = if (meetings.size > limit) meetings.last().id else null
        val items = if (newNext == null) meetings else meetings.subList(0, limit)
        return items to newNext
    }

    private fun createPreparedMeetingInfos(
        items: List<Meeting>,
        myTeamId: UUID,
    ): List<PreparedMeetingInfo> {
        val teamIds = getUniqueTeamIds(items)
        val teamIdToTeamInfo = mapTeamIdToTeamInfo(teamIds)

        return items.map {
            val otherTeamId =
                if (it.requestingTeamId != myTeamId) it.requestingTeamId else it.receivingTeamId
            val otherTeamInfo = teamIdToTeamInfo[otherTeamId]
                ?: throw IllegalArgumentException()
            PreparedMeetingInfo(
                id = it.id,
                memberCount = otherTeamInfo.team.memberCount,
                otherTeam = otherTeamInfo,
                status = it.status,
                createdAt = it.createdAt,
            )
        }
    }

    private fun mapTeamIdToTeamInfo(teamIds: List<UUID>) =
        meetingTeamInfoGetAllByIds.invoke(teamIds)
            .associateBy { it.team.id }

    private fun getUniqueTeamIds(items: List<Meeting>) = items
        .flatMap { listOf(it.receivingTeamId, it.requestingTeamId) }
        .distinct()

}
