package com.studentcenter.weave.infrastructure.persistence.meeting.adapter

import com.studentcenter.weave.application.meeting.port.outbound.MeetingRepository
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.infrastructure.persistence.meeting.entity.MeetingJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.meeting.repository.MeetingJpaRepository
import org.springframework.stereotype.Component

@Component
class MeetingJpaAdapter(
    private val meetingJpaRepository: MeetingJpaRepository,
) : MeetingRepository {

    override fun save(meeting: Meeting) {
        meeting
            .toJpaEntity()
            .let { meetingJpaRepository.save(it) }
    }

}
