package com.studentcenter.weave.infrastructure.persistence.meeting.entity

import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "meeting_attendance")
class MeetingAttendanceJpaEntity(
    id: UUID,
    meetingId: UUID,
    meetingMemberId: UUID,
    isAttend: Boolean,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
) {

    @Id
    @Column(nullable = false)
    var id: UUID = id
        private set

    @Column(nullable = false, updatable = false)
    var meetingId: UUID = meetingId
        private set

    @Column(nullable = false)
    var meetingMemberId: UUID = meetingMemberId
        private set

    @Column(nullable = false)
    var isAttend: Boolean = isAttend
        private set

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = createdAt
        private set

    @Column(nullable = false)
    var updatedAt: LocalDateTime = updatedAt
        private set

    fun toDomain(): MeetingAttendance {
        return MeetingAttendance(
            id = id,
            meetingId = meetingId,
            meetingMemberId = meetingMemberId,
            isAttend = isAttend,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    companion object {

        fun MeetingAttendance.toJpaEntity(): MeetingAttendanceJpaEntity {
            return MeetingAttendanceJpaEntity(
                id = id,
                meetingId = meetingId,
                meetingMemberId = meetingMemberId,
                isAttend = isAttend,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }

    }

}
