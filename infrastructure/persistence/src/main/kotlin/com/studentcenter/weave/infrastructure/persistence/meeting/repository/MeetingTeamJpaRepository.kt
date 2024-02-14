package com.studentcenter.weave.infrastructure.persistence.meeting.repository

import com.studentcenter.weave.infrastructure.persistence.meeting.entity.MeetingTeamJpaEntity
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
    fun findAllByMemberUserId(
        memberUserId: UUID,
        next: UUID?,
        limit: Int,
    ): List<MeetingTeamJpaEntity>


}
