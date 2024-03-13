package com.studentcenter.weave.infrastructure.persistence.meeting.adapter

import com.studentcenter.weave.application.meeting.port.outbound.MeetingRepository
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.enums.TeamType
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceExceptionType
import com.studentcenter.weave.infrastructure.persistence.meeting.entity.MeetingJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.meeting.repository.MeetingJpaRepository
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.data.repository.findByIdOrNull
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

    override fun getById(id: UUID): Meeting {
        return meetingJpaRepository.findByIdOrNull(id)?.toDomain()
            ?: throw CustomException(
                type = PersistenceExceptionType.RESOURCE_NOT_FOUND,
                message = "Meeting(id=$id)를 찾을 수 없습니다"
            )
    }

    override fun findByRequestingTeamIdAndReceivingTeamId(
        requestingTeamId: UUID,
        receivingTeamId: UUID,
    ): Meeting? {
        return meetingJpaRepository
            .findByRequestingTeamIdAndReceivingTeamId(
                requestingTeamId = requestingTeamId,
                receivingTeamId = receivingTeamId,
            )?.toDomain()
    }

    override fun cancelAllNotFinishedMeetingByTeamId(teamId: UUID) {
        return meetingJpaRepository.cancelAllNotFinishedMeetingByTeamId(teamId)
    }

    override fun existsMeetingRequest(
        requestingTeamId: UUID,
        receivingMeetingTeamId: UUID,
    ): Boolean {
        return meetingJpaRepository.existsByRequestingTeamIdAndReceivingTeamId(
            requestingTeamId,
            receivingMeetingTeamId,
        )
    }

    override fun findAllPreparedMeetingByTeamId(
        teamId: UUID,
        next: UUID?,
        limit: Int,
    ): List<Meeting> {
        return meetingJpaRepository.findAllPreparedMeetings(
            teamId = teamId,
            next = next,
            limit = limit,
        ).map { it.toDomain() }
    }

    override fun cancelEndedPendingMeeting() = meetingJpaRepository.cancelEndedPendingMeeting()

}
