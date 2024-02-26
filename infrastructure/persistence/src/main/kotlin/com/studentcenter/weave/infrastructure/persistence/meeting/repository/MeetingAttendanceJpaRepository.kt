package com.studentcenter.weave.infrastructure.persistence.meeting.repository

import com.studentcenter.weave.infrastructure.persistence.meeting.entity.MeetingAttendanceJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MeetingAttendanceJpaRepository : JpaRepository<MeetingAttendanceJpaEntity, UUID> {

    fun findAllByMeetingId(meetingId: UUID): List<MeetingAttendanceJpaEntity>

    fun countByMeetingIdAndIsAttendIsTrue(meetingId: UUID): Int

    fun existsByMeetingIdAndMeetingMemberId(meetingId: UUID, meetingMemberId: UUID) : Boolean

}
