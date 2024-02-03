package com.studentcenter.weave.infrastructure.persistence.meeting.entity

import com.studentcenter.weave.domain.meeting.enums.Location
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
    location: Location,
) {

    @Id
    @Column
    var id: UUID = id
        private set

    @Column(nullable = false)
    var teamIntroduce: TeamIntroduce = teamIntroduce
        private set

    @Column
    var leaderUserId: UUID = leaderUserId
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

}
