package com.studentcenter.weave.application.meeting.service.domain

import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import java.util.*

interface MeetingAttendanceDomainService {

    fun findAllByMeetingId(meetingId: UUID): List<MeetingAttendance>

    fun countByMeetingId(meetingId: UUID): Int

    fun save(meetingAttendance: MeetingAttendance)

    fun existsByMeetingIdAndMeetingMemberId(meetingId: UUID, meetingMemberId: UUID): Boolean

}
