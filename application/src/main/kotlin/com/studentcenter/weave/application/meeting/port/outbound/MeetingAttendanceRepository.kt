package com.studentcenter.weave.application.meeting.port.outbound

import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import java.util.*

interface MeetingAttendanceRepository {

    fun findAllByMeetingId(teamId: UUID): List<MeetingAttendance>

}
