package com.studentcenter.weave.application.meeting.service.domain

import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.enums.TeamType
import java.util.*

interface MeetingDomainService {

    fun save(meeting: Meeting)

    fun findAllPendingMeetingByTeamId(
        teamId: UUID,
        teamType: TeamType,
        next: UUID?,
        limit: Int,
    ): List<Meeting>

    fun findAllPreparedMeetingByTeamId(
        teamId: UUID,
        next: UUID?,
        limit: Int,
    ): List<Meeting>

    fun getById(id: UUID): Meeting

    fun findByRequestingTeamIdAndReceivingTeamId(
        requestingTeamId: UUID,
        receivingTeamId: UUID,
    ): Meeting?

    fun cancelAllNotFinishedMeetingByTeamId(teamId: UUID)

    fun existsMeetingRequest(requestingTeamId: UUID, receivingMeetingTeamId: UUID): Boolean

    fun countByStatusIsCompleted(): Int

}
