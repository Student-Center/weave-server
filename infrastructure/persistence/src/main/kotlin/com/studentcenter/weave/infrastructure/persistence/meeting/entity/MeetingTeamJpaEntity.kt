package com.studentcenter.weave.infrastructure.persistence.meeting.entity

import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.enums.Gender
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "meeting_team")
class MeetingTeamJpaEntity(
    id: UUID,
    teamIntroduce: TeamIntroduce,
    memberCount: Int,
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
                location = location,
                status = status,
                gender = gender,
            )
        }

    }

}
