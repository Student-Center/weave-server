package com.studentcenter.weave.infrastructure.persistence.meeting.adapter

import com.studentcenter.weave.application.meeting.port.outbound.MeetingAttendanceRepository
import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import com.studentcenter.weave.infrastructure.persistence.meeting.entity.MeetingAttendanceJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.meeting.repository.MeetingAttendanceJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class MeetingAttendanceJpaAdapter(
    private val meetingAttendanceJpaRepository: MeetingAttendanceJpaRepository,
) : MeetingAttendanceRepository {

    override fun findAllByMeetingId(meetingId: UUID): List<MeetingAttendance> {
        return meetingAttendanceJpaRepository
            .findAllByMeetingId(meetingId)
            .map { it.toDomain() }
    }

    override fun countByMeetingIdAndIsAttend(meetingId: UUID): Int {
        return meetingAttendanceJpaRepository.countByMeetingIdAndIsAttendIsTrue(meetingId)
    }

    override fun save(meetingAttendance: MeetingAttendance) {
        meetingAttendanceJpaRepository.save(meetingAttendance.toJpaEntity())
    }

    override fun existsByMeetingIdAndMeetingMemberId(
        meetingId: UUID,
        meetingMemberId: UUID,
    ): Boolean {
        return meetingAttendanceJpaRepository.existsByMeetingIdAndMeetingMemberId(
            meetingId = meetingId,
            meetingMemberId = meetingMemberId,
        )
    }

}
