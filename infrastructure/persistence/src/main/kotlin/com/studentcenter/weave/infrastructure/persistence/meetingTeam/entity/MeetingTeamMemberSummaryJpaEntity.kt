package com.studentcenter.weave.infrastructure.persistence.meetingTeam.entity

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Mbti
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
    youngestMemberBirthYear: Int,
    oldestMemberBirthYear: Int,
    createdAt: LocalDateTime,
) {

    @Id
    @Column(name = "id")
    var id: UUID = id
        private set

    @Column(name = "meeting_team_id", nullable = false, updatable = false)
    var meetingTeamId: UUID = meetingTeamId
        private set

    @Column(
        name = "team_mbti",
        nullable = false,
        columnDefinition = "varchar(255)",
        updatable = false
    )
    var teamMbti: String = teamMbti
        private set

    @Column(name = "youngest_member_birth_year", nullable = false, updatable = false)
    var youngestMemberBirthYear: Int = youngestMemberBirthYear
        private set

    @Column(name = "oldest_member_birth_year", nullable = false, updatable = false)
    var oldestMemberBirthYear: Int = oldestMemberBirthYear
        private set

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = createdAt
        private set

    companion object {

        fun MeetingTeamMemberSummary.toJpaEntity(): MeetingTeamMemberSummaryJpaEntity {
            return MeetingTeamMemberSummaryJpaEntity(
                id = id,
                meetingTeamId = meetingTeamId,
                teamMbti = teamMbti.value,
                youngestMemberBirthYear = youngestMemberBirthYear.value,
                oldestMemberBirthYear = oldestMemberBirthYear.value,
                createdAt = createdAt,
            )
        }
    }

    fun toDomain(): MeetingTeamMemberSummary {
        return MeetingTeamMemberSummary(
            id = id,
            meetingTeamId = meetingTeamId,
            teamMbti = Mbti(teamMbti),
            youngestMemberBirthYear = BirthYear(youngestMemberBirthYear),
            oldestMemberBirthYear = BirthYear(oldestMemberBirthYear),
            createdAt = createdAt,
        )
    }

}
