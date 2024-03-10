package com.studentcenter.weave.infrastructure.persistence.meeting.repository

import com.studentcenter.weave.infrastructure.persistence.meeting.entity.MeetingJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MeetingJpaRepository : JpaRepository<MeetingJpaEntity, UUID> {

    @Query(
        value = """
            SELECT m.*
            FROM meeting m
            where m.requesting_team_id = :teamId 
            AND (:next IS NULL or id <= :next)
            AND m.status = 'PENDING'
            ORDER BY m.id DESC
            LIMIT :limit
        """,
        nativeQuery = true
    )
    fun findAllRequestingPendingMeeting(
        @Param("teamId") teamId: UUID,
        @Param("next") next: UUID?,
        @Param("limit") limit: Int,
    ): List<MeetingJpaEntity>


    @Query(
        value = """
            SELECT m.*
            FROM meeting m
            where m.receiving_team_id = :teamId 
            AND (:next IS NULL or id <= :next)
            AND m.status = 'PENDING'
            ORDER BY m.id DESC
            LIMIT :limit
        """,
        nativeQuery = true,
    )
    fun findAllReceivingPendingMeeting(
        @Param("teamId") teamId: UUID,
        @Param("next") next: UUID?,
        @Param("limit") limit: Int,
    ): List<MeetingJpaEntity>

    fun findByRequestingTeamIdAndReceivingTeamId(
        requestingTeamId: UUID,
        receivingTeamId: UUID,
    ): MeetingJpaEntity?


    @Modifying
    @Query(value =
        """
        UPDATE meeting
        SET status = 'CANCELED', finished_at = now()
        WHERE (requesting_team_id = :teamId or receiving_team_id = :teamId)
        AND status NOT IN ('COMPLETED', 'CANCELED') 
        """,
        nativeQuery = true
    )
    fun cancelAllNotFinishedMeetingByTeamId(@Param("teamId") teamId: UUID)

    fun existsByRequestingTeamIdAndReceivingTeamId(
        requestingTeamId: UUID,
        receivingMeetingTeamId: UUID,
    ): Boolean


    // FIXME(prepared): 추후에 상태가 추가되면 Completed -> Prepared
    @Query(
        value = """
            SELECT m.*
            FROM meeting as m
            WHERE (m.requesting_team_id = :teamId or m.receiving_team_id = :teamId)
            AND (:next is null or id <= :next)
            AND m.status = 'COMPLETED'
            ORDER BY m.id DESC
            LIMIT :limit
        """,
        nativeQuery = true
    )
    fun findAllPreparedMeetings(
        @Param("teamId") teamId: UUID,
        @Param("next") next: UUID?,
        @Param("limit") limit: Int,
    ): List<MeetingJpaEntity>
}
