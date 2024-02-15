package com.studentcenter.weave.application.meetingTeam.port.outbound

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import java.util.*

interface MeetingTeamRepository {

    fun save(meetingTeam: MeetingTeam)

    fun getById(id: UUID): MeetingTeam

    fun scrollByMemberUserId(
        userId: UUID,
        next: UUID?,
        limit: Int,
    ): List<MeetingTeam>

    fun deleteById(id: UUID)

}
