package com.studentcenter.weave.infrastructure.persistence.meeting.adapter

import com.studentcenter.weave.application.meeting.port.outbound.MeetingAttendanceRepository
import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import com.studentcenter.weave.infrastructure.persistence.meeting.entity.MeetingAttendanceJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.meeting.repository.MeetingAttendanceJpaRepository
import org.springframework.stereotype.Component

@Component
class MeetingAttendanceJpaAdapter(
    private val meetingAttendanceJpaRepository: MeetingAttendanceJpaRepository
) : MeetingAttendanceRepository {

    override fun saveAll(meetingAttendances: List<MeetingAttendance>) {
        meetingAttendances
            .map { it.toJpaEntity() }
            .also { meetingAttendanceJpaRepository.saveAll(it) }
    }
}
