package com.studentcenter.weave.application.meeting.port.outbound

import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import java.util.*

interface MeetingTeamRepository {

    fun save(meetingTeam: MeetingTeam)

    fun getById(id: UUID): MeetingTeam

    fun scrollByMemberUserId(
        userId: UUID,
        next: UUID?,
        limit: Int,
    ): List<MeetingTeam>

}
