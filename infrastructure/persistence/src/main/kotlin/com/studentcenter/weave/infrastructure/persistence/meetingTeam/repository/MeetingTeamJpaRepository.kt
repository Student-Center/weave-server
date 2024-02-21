package com.studentcenter.weave.infrastructure.persistence.meetingTeam.repository

import com.studentcenter.weave.infrastructure.persistence.meetingTeam.entity.MeetingTeamJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MeetingTeamJpaRepository : JpaRepository<MeetingTeamJpaEntity, UUID> {

    // TODO : JOOQ 설정 이후 변경 필요
    @Query(
        value =
        """
            SELECT mt.id, mt.team_introduce, mt.member_count, mt.location, mt.status, mt.gender
            FROM meeting_team mt
            INNER JOIN meeting_member mm ON mt.id = mm.meeting_team_id
            WHERE mm.user_id = :memberUserId
        """,
        nativeQuery = true,
    )
    fun findByMemberUserId(memberUserId: UUID): Optional<MeetingTeamJpaEntity>

    // TODO : JOOQ 설정 이후 변경 필요
    @Query(
        value =
        """
            SELECT mt.id, mt.team_introduce, mt.member_count, mt.location, mt.status, mt.gender
            FROM meeting_team mt
            INNER JOIN meeting_member mm ON mt.id = mm.meeting_team_id
            WHERE mm.user_id = :memberUserId
            AND (:next IS NULL OR mt.id > :next)
            ORDER BY mt.id DESC
            LIMIT :limit
        """,
        nativeQuery = true
    )
    fun scrollByMemberUserId(
        memberUserId: UUID,
        next: UUID?,
        limit: Int,
    ): List<MeetingTeamJpaEntity>


    // TODO : JOOQ 설정 이후 변경 필요
    @Query(
        value =
        """
            SELECT mt.id, mt.team_introduce, mt.member_count, mt.location, mt.status, mt.gender
            FROM (
                SELECT *
                FROM meeting_team
                WHERE (:memberCount IS NULL OR member_count = :memberCount)
                  AND (:next IS NULL OR id > :next)
                  AND (:status IS NULL OR status = :status)
                  AND (:gender IS NULL OR gender = :gender)
                  AND (:preferredLocations IS NULL OR location IN (:preferredLocations))
            ) mt
            INNER JOIN weave.meeting_team_member_summary mtms ON mt.id = mtms.meeting_team_id
            WHERE (mtms.youngest_member_birth_year <= :youngestMemberBirthYear)
              AND (mtms.oldest_member_birth_year >= :oldestMemberBirthYear)
            ORDER BY mt.id DESC
            LIMIT :limit
        """,
        nativeQuery = true
    )
    fun scrollByFilter(
        memberCount: Int?,
        youngestMemberBirthYear: Int,
        oldestMemberBirthYear: Int,
        preferredLocations: List<String>?,
        gender: String?,
        status: String?,
        next: UUID?,
        limit: Int
    ): List<MeetingTeamJpaEntity>

}
