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
            FROM meeting_team mt
            INNER JOIN weave.meeting_team_member_summary mtms ON mt.id = mtms.meeting_team_id
            WHERE (:memberCount IS NULL OR mt.member_count = :memberCount)
            AND (:youngestMemberBirthYear IS NULL OR mtms.youngest_member_birth_year <= :youngestMemberBirthYear)
            AND (:oldestMemberBirthYear IS NULL OR mtms.oldest_member_birth_year >= :oldestMemberBirthYear)
            AND (:preferredLocations IS NULL OR mt.location IN (:preferredLocations))
            AND (:next IS NULL OR mt.id > :next)
            AND (:status IS NULL OR mt.status = :status)
            AND (:gender IS NULL OR mt.gender = :gender)
            
            ORDER BY mt.id DESC
            LIMIT :limit
        """,
        nativeQuery = true
    )
    fun scrollByFilter(
        memberCount: Int?,
        youngestMemberBirthYear: Int?,
        oldestMemberBirthYear: Int?,
        preferredLocations: List<String>?,
        gender: String?,
        status: String?,
        next: UUID?,
        limit: Int
    ): List<MeetingTeamJpaEntity>

}
