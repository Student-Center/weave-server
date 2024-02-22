package com.studentcenter.weave.infrastructure.persistence.meeting.adapter

import com.studentcenter.weave.application.meeting.port.outbound.MeetingRepository
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.infrastructure.persistence.meeting.entity.MeetingJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.meeting.repository.MeetingJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class MeetingJpaAdapter(
    private val meetingJpaRepository: MeetingJpaRepository,
) : MeetingRepository {

    override fun save(meeting: Meeting) {
        meeting
            .toJpaEntity()
            .let { meetingJpaRepository.save(it) }
    }

    override fun scrollPendingMeetingByUserId(
        userId: UUID,
        isRequester: Boolean,
        next: UUID?,
        limit: Int,
    ): List<Meeting> {
        val teamEntities = if (isRequester) meetingJpaRepository.scrollRequestingPendingMeetingByUserId(
            userId = userId,
            next = next,
            limit = limit + 1,
        ) else meetingJpaRepository.scrollReceivingPendingMeetingByUserId(
            userId = userId,
            next = next,
            limit = limit + 1,
        )

        return teamEntities.map { it.toDomain() }
    }

}
