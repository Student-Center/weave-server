package com.studentcenter.weave.infrastructure.persistence.meetingTeam.adapter

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamMemberSummaryRepository
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceException
import com.studentcenter.weave.infrastructure.persistence.meetingTeam.entity.MeetingTeamMemberSummaryJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.meetingTeam.repository.MeetingTeamMemberSummaryJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class MeetingTeamMemberSummaryJpaAdapter(
    private val meetingTeamMemberSummaryJpaRepository: MeetingTeamMemberSummaryJpaRepository,
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
            .orElseThrow { PersistenceException.ResourceNotFound("MeetingTeamMemberSummary(id=$id)를 찾을 수 없습니다.") }
            .toDomain()
    }

    override fun getByMeetingTeamId(meetingTeamId: UUID): MeetingTeamMemberSummary {
        return meetingTeamMemberSummaryJpaRepository
            .findByMeetingTeamId(meetingTeamId)
            ?.toDomain()
            ?: throw PersistenceException.ResourceNotFound("MeetingTeamMemberSummary(meetingTeamId=$meetingTeamId)를 찾을 수 없습니다.")
    }

}
