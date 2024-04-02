package com.studentcenter.weave.infrastructure.persistence.meetingTeam.entity

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.infrastructure.persistence.meetingTeam.entity.MeetingMemberJpaEntity.Companion.toJpaEntity
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "meeting_team")
class MeetingTeamJpaEntity(
    id: UUID,
    teamIntroduce: TeamIntroduce,
    memberCount: Int,
    members: List<MeetingMemberJpaEntity>,
    location: Location,
    status: MeetingTeamStatus,
    gender: Gender,
) {

    @Id
    @Column(nullable = false)
    var id: UUID = id
        private set

    @Column(nullable = false)
    var teamIntroduce: TeamIntroduce = teamIntroduce
        private set

    @Column(nullable = false)
    var memberCount: Int = memberCount
        private set

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "meeting_member",
        joinColumns = [JoinColumn(name = "meeting_team_id")]
    )
    var members: List<MeetingMemberJpaEntity> = members
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    var location: Location = location
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    var status: MeetingTeamStatus = status
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    var gender: Gender = gender
        private set

    fun toDomain(): MeetingTeam {
        return MeetingTeam(
            id = id,
            teamIntroduce = teamIntroduce,
            memberCount = memberCount,
            members = members.map { it.toDomain() },
            location = location,
            status = status,
            gender = gender,
        )
    }

    companion object {

        fun MeetingTeam.toJpaEntity(): MeetingTeamJpaEntity {
            return MeetingTeamJpaEntity(
                id = id,
                teamIntroduce = teamIntroduce,
                memberCount = memberCount,
                members = members.map { it.toJpaEntity() },
                location = location,
                status = status,
                gender = gender,
            )
        }

    }

}
