package com.studentcenter.weave.domain.meeting.entity

import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

data class MeetingAttendance(
    val id: UUID = UuidCreator.create(),
    val meetingId: UUID,
    val meetingMemberId: UUID,
    val isAttend: Boolean,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = createdAt,
) {

    companion object {

        fun create(
            meetingId: UUID,
            meetingMemberId: UUID,
            isAttend: Boolean = false,
        ): MeetingAttendance {
            return MeetingAttendance(
                meetingId = meetingId,
                meetingMemberId = meetingMemberId,
                isAttend = isAttend,
            )
        }
    }

}
