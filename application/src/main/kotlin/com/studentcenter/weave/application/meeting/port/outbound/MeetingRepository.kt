package com.studentcenter.weave.application.meeting.port.outbound

import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.enums.TeamType
import java.util.*

interface MeetingRepository {

    fun save(meeting: Meeting)

    fun findAllPendingMeetingByTeamId(
        teamId: UUID,
        teamType: TeamType,
        next: UUID?,
        limit: Int,
    ): List<Meeting>

    fun getById(id: UUID): Meeting

    fun findByRequestingTeamIdAndReceivingTeamId(
        requestingTeamId: UUID,
        receivingTeamId: UUID,
    ): Meeting?

    fun cancelAllNotFinishedMeetingByTeamId(teamId: UUID)

}
