package com.studentcenter.weave.infrastructure.persistence.meetingTeam.adapter

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamMemberSummaryRepository
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceExceptionType
import com.studentcenter.weave.infrastructure.persistence.meetingTeam.entity.MeetingTeamMemberSummaryJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.meetingTeam.repository.MeetingTeamMemberSummaryJpaRepository
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Component
import java.util.*

@Component
class MeetingTeamMemberSummaryJpaAdapter(
    private val meetingTeamMemberSummaryJpaRepository: MeetingTeamMemberSummaryJpaRepository
) : MeetingTeamMemberSummaryRepository {

    override fun save(meetingTeamMemberSummary: MeetingTeamMemberSummary) {
        meetingTeamMemberSummaryJpaRepository.save(meetingTeamMemberSummary.toJpaEntity())
    }

    override fun deleteById(id: UUID) {
        meetingTeamMemberSummaryJpaRepository.deleteById(id)
    }

    override fun getById(id: UUID): MeetingTeamMemberSummary {
        return meetingTeamMemberSummaryJpaRepository
            .findById(id)
            .orElseThrow {
                CustomException(
                    type = PersistenceExceptionType.RESOURCE_NOT_FOUND,
                    message = "MeetingTeamMemberSummary(id=$id)를 찾을 수 없습니다."
                )
            }
            .toDomain()
    }

    override fun getByMeetingTeamId(meetingTeamId: UUID): MeetingTeamMemberSummary {
        return meetingTeamMemberSummaryJpaRepository
            .findByMeetingTeamId(meetingTeamId)
            ?.toDomain()
            ?: throw CustomException(
                type = PersistenceExceptionType.RESOURCE_NOT_FOUND,
                message = "MeetingTeamMemberSummary(meetingTeamId=$meetingTeamId)를 찾을 수 없습니다."
            )
    }

}
