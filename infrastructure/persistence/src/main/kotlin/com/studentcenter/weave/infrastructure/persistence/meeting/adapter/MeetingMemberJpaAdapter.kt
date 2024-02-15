package com.studentcenter.weave.infrastructure.persistence.meeting.adapter

import com.studentcenter.weave.application.meeting.port.outbound.MeetingMemberRepository
import com.studentcenter.weave.domain.meeting.entity.MeetingMember
import com.studentcenter.weave.domain.meeting.enums.MeetingMemberRole
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceExceptionType
import com.studentcenter.weave.infrastructure.persistence.meeting.entity.MeetingMemberJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.meeting.repository.MeetingMemberJpaRepository
import com.studentcenter.weave.support.common.exception.CustomException
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

    override fun getLeaderByMeetingTeamId(meetingTeamId: UUID): MeetingMember {
        return meetingMemberJpaRepository
            .findByMeetingTeamIdAndRole(meetingTeamId, MeetingMemberRole.LEADER)
            ?.toDomain()
            ?: throw CustomException(PersistenceExceptionType.RESOURCE_NOT_FOUND, "팀장을 찾을 수 없어요")
    }

}
