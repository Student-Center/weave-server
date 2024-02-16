package com.studentcenter.weave.infrastructure.persistence.meetingTeam.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "meeting_team_member_summary")
class MeetingTeamMemberSummaryJpaEntity(
    id: UUID,
    meetingTeamId: UUID,
    teamMbti: String,
    minAge: Int,
    maxAge: Int,
    createdAt: LocalDateTime,
) {

    @Id
    @Column(name = "id")
    var id: UUID = id
        private set

    @Column(name = "meeting_team_id", nullable = false)
    var meetingTeamId: UUID = meetingTeamId
        private set

    @Column(name = "team_mbti", nullable = false, columnDefinition = "varchar(255)")
    var teamMbti: String = teamMbti
        private set

    @Column(name = "min_age", nullable = false)
    var minAge: Int = minAge
        private set

    @Column(name = "max_age", nullable = false)
    var maxAge: Int = maxAge
        private set

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = createdAt
        private set

}
