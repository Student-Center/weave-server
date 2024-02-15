package com.studentcenter.weave.infrastructure.persistence.meetingTeam.entity

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "meeting_member")
class MeetingMemberJpaEntity(
    id: UUID,
    meetingTeamId: UUID,
    userId: UUID,
    role: MeetingMemberRole,
) {

    @Id
    var id: UUID = id
        private set

    @Column(nullable = false)
    var meetingTeamId = meetingTeamId
        private set

    @Column(nullable = false)
    var userId = userId
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    var role = role
        private set

    fun toDomain(): MeetingMember {
        return MeetingMember(
            id = id,
            meetingTeamId = meetingTeamId,
            userId = userId,
            role = role,
        )
    }

    companion object {
        fun MeetingMember.toJpaEntity(): MeetingMemberJpaEntity {
            return MeetingMemberJpaEntity(
                id = id,
                meetingTeamId = meetingTeamId,
                userId = userId,
                role = role,
            )
        }
    }

}
