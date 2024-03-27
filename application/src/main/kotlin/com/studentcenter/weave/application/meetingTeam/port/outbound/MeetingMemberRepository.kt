package com.studentcenter.weave.application.meetingTeam.port.outbound

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import java.util.*

interface MeetingMemberRepository {

    fun save(meetingMember: MeetingMember)

    fun countByMeetingTeamId(meetingTeamId: UUID): Int

    fun findByMeetingTeamIdAndUserId(
        meetingTeamId: UUID,
        userId: UUID
    ): MeetingMember?

    fun findByUserId(userId: UUID): MeetingMember?

    fun findAllByMeetingTeamId(meetingTeamId: UUID): List<MeetingMember>

    fun deleteAllByMeetingTeamId(meetingTeamId: UUID)

    fun deleteById(id: UUID)

    fun findAllByMeetingTeamIds(teamIds: List<UUID>): List<MeetingMember>

}
