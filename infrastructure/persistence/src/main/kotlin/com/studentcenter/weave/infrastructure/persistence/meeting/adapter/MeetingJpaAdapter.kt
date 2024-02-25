package com.studentcenter.weave.infrastructure.persistence.meeting.adapter

import com.studentcenter.weave.application.meeting.port.outbound.MeetingRepository
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.enums.TeamType
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

    override fun findAllPendingMeetingByTeamId(
        teamId: UUID,
        teamType: TeamType,
        next: UUID?,
        limit: Int,
    ): List<Meeting> {
        val teamEntities = if (teamType == TeamType.REQUESTING) {
            meetingJpaRepository.findAllRequestingPendingMeeting(
                teamId = teamId,
                next = next,
                limit = limit,
            )
        } else {
            meetingJpaRepository.findAllReceivingPendingMeeting(
                teamId = teamId,
                next = next,
                limit = limit,
            )
        }

        return teamEntities.map { it.toDomain() }
    }

}
