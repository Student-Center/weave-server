package com.studentcenter.weave.infrastructure.persistence.meeting.repository

import com.studentcenter.weave.infrastructure.persistence.meeting.entity.MeetingMemberJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MeetingMemberJpaRepository : JpaRepository<MeetingMemberJpaEntity, UUID> {

    fun countByMeetingTeamId(meetingTeamId: UUID): Int

    fun findByMeetingTeamIdAndUserId(
        meetingTeamId: UUID,
        userId: UUID
    ): MeetingMemberJpaEntity?

    fun findAllByMeetingTeamId(meetingTeamId: UUID): List<MeetingMemberJpaEntity>

    fun deleteAllByMeetingTeamId(id: UUID)

}
