package com.studentcenter.weave.application.meeting.port.inbound

import java.util.*

fun interface MeetingAttendanceCreateUseCase {

    fun invoke(meetingId: UUID, attendance: Boolean)

}
