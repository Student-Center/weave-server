package com.studentcenter.weave.domain.meeting.entity

import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

object MeetingAttendanceFixtureFactory {

    fun create(
        id: UUID = UuidCreator.create(),
        meetingId: UUID = UuidCreator.create(),
        meetingMemberId: UUID = UuidCreator.create(),
        isAttend: Boolean = true,
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = LocalDateTime.now(),
    ): MeetingAttendance {
        return MeetingAttendance(
            id = id,
            meetingId = meetingId,
            meetingMemberId = meetingMemberId,
            isAttend = isAttend,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
