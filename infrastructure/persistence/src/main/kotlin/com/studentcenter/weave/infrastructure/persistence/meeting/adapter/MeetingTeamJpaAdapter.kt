package com.studentcenter.weave.infrastructure.persistence.meeting.adapter

import com.studentcenter.weave.application.meeting.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceExceptionType
import com.studentcenter.weave.infrastructure.persistence.meeting.entity.MeetingTeamJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.meeting.repository.MeetingTeamJpaRepository
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Component
import java.util.*

@Component
class MeetingTeamJpaAdapter(
    private val meetingTeamJpaRepository: MeetingTeamJpaRepository,
) : MeetingTeamRepository {

    override fun save(meetingTeam: MeetingTeam) {
        meetingTeam
            .toJpaEntity()
            .let { meetingTeamJpaRepository.save(it) }
    }

    override fun getById(id: UUID): MeetingTeam {
        return meetingTeamJpaRepository
            .findById(id)
            .orElseThrow() {
                CustomException(
                    type = PersistenceExceptionType.RESOURCE_NOT_FOUND,
                    message = "MeetingTeam(id=$id)를 찾을 수 없습니다"
                )
            }.toDomain()
    }

    override fun scrollByMemberUserId(
        userId: UUID,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam> {
        val result = meetingTeamJpaRepository
            .scrollByMemberUserId(userId, next, limit)
            .map { it.toDomain() }

        return result
    }

    override fun deleteById(id: UUID) {
        meetingTeamJpaRepository.deleteById(id)
    }


}
