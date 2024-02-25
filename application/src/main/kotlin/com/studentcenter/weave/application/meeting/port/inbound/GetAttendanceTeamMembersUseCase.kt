package com.studentcenter.weave.application.meeting.port.inbound

import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import java.util.*

fun interface GetAttendanceTeamMembersUseCase {

    fun invoke(teamId: UUID) : List<MeetingAttendance>

}
