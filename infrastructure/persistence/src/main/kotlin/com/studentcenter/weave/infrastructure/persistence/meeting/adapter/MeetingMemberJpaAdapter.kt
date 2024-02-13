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
): MeetingMemberRepository {

    override fun save(meetingMember: MeetingMember) {
        meetingMemberJpaRepository.save(meetingMember.toJpaEntity())
    }

    override fun countByMeetingTeamId(meetingTeamId: UUID): Int {
        return meetingMemberJpaRepository.countByMeetingTeamId(meetingTeamId)
    }

}
