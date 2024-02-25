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
            where m.requesting_team_id = :teamId 
            AND (:next is null or id < :next)
            AND m.status = 'PENDING'
            LIMIT :limit
        """,
        nativeQuery = true,
    )
    fun findAllRequestingPendingMeeting(
        @Param("teamId") teamId: UUID,
        @Param("next") next: UUID?,
        @Param("limit") limit: Int,
    ): List<MeetingJpaEntity>


    @Query(
        value = """
            SELECT m.*
            FROM meeting as m
            where m.receiving_team_id = :teamId 
            AND (:next is null or id < :next)
            AND m.status = 'PENDING'
            LIMIT :limit
        """,
        nativeQuery = true,
    )
    fun findAllReceivingPendingMeeting(
        @Param("teamId") teamId: UUID,
        @Param("next") next: UUID?,
        @Param("limit") limit: Int,
    ): List<MeetingJpaEntity>

}
