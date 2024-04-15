package com.studentcenter.weave.application.meeting.port.inbound

import java.util.*

fun interface CreateMeetingAttendance {

    fun invoke(meetingId: UUID, attendance: Boolean)

}
