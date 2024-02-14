package com.studentcenter.weave.infrastructure.persistence.meeting.adapter

import com.studentcenter.weave.application.meeting.port.outbound.MeetingMemberRepository
import com.studentcenter.weave.domain.meeting.entity.MeetingMember
import com.studentcenter.weave.infrastructure.persistence.meeting.entity.MeetingMemberJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.meeting.repository.MeetingMemberJpaRepository
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

    override fun deleteAllByMeetingTeamId(id: UUID) {
        meetingMemberJpaRepository.deleteAllByMeetingTeamId(id)
    }

}
