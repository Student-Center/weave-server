package com.studentcenter.weave.application.meeting.port.outbound

import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance

interface MeetingAttendanceRepository {

    fun saveAll(meetingAttendances: List<MeetingAttendance>)

}
