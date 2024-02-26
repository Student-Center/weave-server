package com.studentcenter.weave.infrastructure.persistence.meetingTeam.adapter

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingMemberRepository
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.infrastructure.persistence.meetingTeam.entity.MeetingMemberJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.meetingTeam.repository.MeetingMemberJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class MeetingMemberJpaAdapter(
    private val meetingMemberJpaRepository: MeetingMemberJpaRepository,
) : MeetingMemberRepository {

    override fun save(meetingMember: MeetingMember) {
        meetingMemberJpaRepository.save(meetingMember.toJpaEntity())
    }

    override fun countByMeetingTeamId(meetingTeamId: UUID): Int {
        return meetingMemberJpaRepository.countByMeetingTeamId(meetingTeamId)
    }

    override fun findByMeetingTeamIdAndUserId(
        meetingTeamId: UUID,
        userId: UUID
    ): MeetingMember? {
        return meetingMemberJpaRepository.findByMeetingTeamIdAndUserId(meetingTeamId, userId)
            ?.toDomain()
    }

    override fun findAllByMeetingTeamId(meetingTeamId: UUID): List<MeetingMember> {
        return meetingMemberJpaRepository
            .findAllByMeetingTeamId(meetingTeamId)
            .map { it.toDomain() }
    }

    override fun deleteAllByMeetingTeamId(meetingTeamId: UUID) {
        meetingMemberJpaRepository.deleteAllByMeetingTeamId(meetingTeamId)
    }

    override fun deleteById(id: UUID) {
        meetingMemberJpaRepository.deleteById(id)
    }

    override fun findAllByMeetingTeamIds(teamIds: List<UUID>): List<MeetingMember> {
        return meetingMemberJpaRepository
            .findAllByMeetingTeamIdIn(teamIds)
            .map { it.toDomain() }
    }

}
