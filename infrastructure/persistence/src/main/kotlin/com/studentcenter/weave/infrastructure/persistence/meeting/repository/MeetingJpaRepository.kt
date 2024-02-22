package com.studentcenter.weave.infrastructure.persistence.meeting.repository

import com.studentcenter.weave.infrastructure.persistence.meeting.entity.MeetingJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MeetingJpaRepository : JpaRepository<MeetingJpaEntity, UUID> {

    @Query(
        value = """
            SELECT m.*
            FROM meeting as m
            where m.requesting_team_id IN (
                SELECT mm.meeting_team_id
                FROM meeting_member as mm
                WHERE mm.user_id = :userId
            ) AND id < :next
            LIMIT :limit
        """,
        nativeQuery = true,
    )
    fun scrollRequestingPendingMeetingByUserId(
        @Param("userId") userId: UUID,
        @Param("next") next: UUID?,
        @Param("limit") limit: Int,
    ): List<MeetingJpaEntity>


    @Query(
        value = """
            SELECT m.*
            FROM meeting as m
            where m.receiving_team_id IN (
                SELECT mm.meeting_team_id
                FROM meeting_member as mm
                WHERE mm.user_id = :userId
            ) AND id < :next
            LIMIT :limit
        """,
        nativeQuery = true,
    )
    fun scrollReceivingPendingMeetingByUserId(
        userId: UUID,
        next: UUID?,
        limit: Int,
    ): List<MeetingJpaEntity>

}
