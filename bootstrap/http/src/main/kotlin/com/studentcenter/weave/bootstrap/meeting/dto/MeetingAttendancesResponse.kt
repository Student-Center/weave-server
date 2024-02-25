package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import java.util.*

data class MeetingAttendancesResponse(
    val meetingAttendances: List<MemberAttendanceDto>,
) {

    data class MemberAttendanceDto(
        val memberId: UUID,
        val attendance: Boolean,
    ) {
        companion object {
            fun from(domain: MeetingAttendance) = MemberAttendanceDto(
                memberId = domain.meetingMemberId,
                attendance = domain.isAttend,
            )
        }
    }

    companion object {
        fun from(meetingAttendances: List<MeetingAttendance>) : MeetingAttendancesResponse {
            return MeetingAttendancesResponse(meetingAttendances.map(MemberAttendanceDto::from))

        }
    }
}

