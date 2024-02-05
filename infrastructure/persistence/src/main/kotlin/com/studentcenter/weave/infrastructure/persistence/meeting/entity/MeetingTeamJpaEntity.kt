package com.studentcenter.weave.infrastructure.persistence.meeting.entity

import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "meeting_team")
class MeetingTeamJpaEntity(
    id: UUID,
    teamIntroduce: TeamIntroduce,
    leaderUserId: UUID,
    memberUserIds: Set<UUID>,
    memberCount: Int,
    location: Location,
    status: MeetingTeamStatus,
) {

    @Id
    @Column(nullable = false)
    var id: UUID = id
        private set

    @Column(nullable = false)
    var teamIntroduce: TeamIntroduce = teamIntroduce
        private set

    @Column(nullable = false)
    var leaderUserId: UUID = leaderUserId
        private set

    @Column(nullable = false)
    var memberCount: Int = memberCount
        private set

    @ElementCollection
    @CollectionTable(name = "meeting_team_member", joinColumns = [JoinColumn(name = "meeting_team_id")])
    @Column(name = "user_id")
    var memberUserIds: Set<UUID> = memberUserIds
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    var location: Location = location
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    var status: MeetingTeamStatus = status
        private set

    companion object{
        fun MeetingTeam.toJpaEntity(): MeetingTeamJpaEntity {
            return MeetingTeamJpaEntity(
                id = id,
                teamIntroduce = teamIntroduce,
                leaderUserId = leaderUserId,
                memberUserIds = memberUserIds,
                memberCount = memberCount,
                location = location,
                status = status,
            )
        }
    }

}
