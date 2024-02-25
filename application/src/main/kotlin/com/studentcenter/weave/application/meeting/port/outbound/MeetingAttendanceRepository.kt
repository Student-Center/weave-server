package com.studentcenter.weave.application.meeting.port.outbound

import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import java.util.*

interface MeetingAttendanceRepository {

    fun findAllByMeetingId(meetingId: UUID): List<MeetingAttendance>

    fun countByMeetingId(meetingId: UUID): Int

    fun save(meetingAttendance: MeetingAttendance)

    fun existsByMeetingIdAndMeetingMemberId(meetingId: UUID, meetingMemberId: UUID): Boolean

}
