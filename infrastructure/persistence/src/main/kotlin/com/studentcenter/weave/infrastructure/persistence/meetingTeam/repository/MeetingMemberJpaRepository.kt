package com.studentcenter.weave.infrastructure.persistence.meetingTeam.repository

import com.studentcenter.weave.infrastructure.persistence.meetingTeam.entity.MeetingMemberJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
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

    @Modifying
    @Query(
        """
            DELETE FROM MeetingMemberJpaEntity m
            WHERE m.meetingTeamId = :teamId 
        """
    )
    fun deleteAllByMeetingTeamId(
        teamId: UUID
    )

    fun findByUserId(userId: UUID): MeetingMemberJpaEntity?

}
