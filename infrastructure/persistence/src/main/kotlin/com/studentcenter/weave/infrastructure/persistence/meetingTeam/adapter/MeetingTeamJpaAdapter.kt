package com.studentcenter.weave.infrastructure.persistence.meetingTeam.adapter

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamListFilter
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceException
import com.studentcenter.weave.infrastructure.persistence.meetingTeam.entity.MeetingTeamJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.meetingTeam.repository.MeetingTeamJpaRepository
import org.springframework.data.repository.findByIdOrNull
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
        return meetingTeamJpaRepository.findByIdOrNull(id)?.toDomain()
            ?: throw PersistenceException.ResourceNotFound("MeetingTeam(id: $id)를 찾을 수 없습니다.")
    }

    override fun getByIdAndStatus(
        id: UUID,
        status: MeetingTeamStatus,
    ): MeetingTeam {
        return meetingTeamJpaRepository.findByIdAndStatus(id, status)?.toDomain()
            ?: throw PersistenceException.ResourceNotFound("MeetingTeam(id: $id, status: $status)를 찾을 수 없습니다.")
    }

    override fun getByMemberUserId(userId: UUID): MeetingTeam {
        return meetingTeamJpaRepository.findByMemberUserId(userId)?.toDomain()
            ?: throw PersistenceException.ResourceNotFound("MeetingTeam(memberUserId: $userId)를 찾을 수 없습니다.")
    }

    override fun findByMemberUserId(userId: UUID): MeetingTeam? {
        return meetingTeamJpaRepository.findByMemberUserId(userId)?.toDomain()
    }

    override fun scrollByMemberUserId(
        userId: UUID,
        next: UUID?,
        limit: Int,
    ): List<MeetingTeam> {
        return meetingTeamJpaRepository
            .scrollByMemberUserId(userId, next, limit)
            .map { it.toDomain() }
    }

    override fun deleteById(id: UUID) {
        meetingTeamJpaRepository.deleteById(id)
    }

    override fun findAllById(ids: List<UUID>): List<MeetingTeam> {
        return meetingTeamJpaRepository.findAllById(ids).map { it.toDomain() }
    }

    override fun scrollByFilter(
        filter: MeetingTeamListFilter,
        next: UUID?,
        limit: Int,
    ): List<MeetingTeam> {
        return meetingTeamJpaRepository
            .scrollByFilter(
                memberCount = filter.memberCount,
                youngestMemberBirthYear = filter.youngestMemberBirthYear,
                oldestMemberBirthYear = filter.oldestMemberBirthYear,
                preferredLocations = filter.preferredLocations?.map { it.name }?.toTypedArray(),
                gender = filter.gender?.name,
                status = filter.status.name,
                next = next,
                limit = limit
            ).map { it.toDomain() }
    }

}
