package com.studentcenter.weave.application.meeting.port.outbound

import com.studentcenter.weave.domain.meeting.entity.MeetingMember
import java.util.*

interface MeetingMemberRepository {

    fun save(meetingMember: MeetingMember)

    fun countByMeetingTeamId(meetingTeamId: UUID): Int

    fun findByMeetingTeamIdAndUserId(
        meetingTeamId: UUID,
        userId: UUID
    ): MeetingMember?

    fun findAllMeetingMembersByMeetingTeamId(meetingTeamId: UUID): List<MeetingMember>

}
