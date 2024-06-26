package com.studentcenter.weave.application.meeting.port.inbound

import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import java.util.*

fun interface GetMeetingAttendances {

    fun invoke(meetingId: UUID): List<MeetingAttendance>

}
