package com.studentcenter.weave.infrastructure.persistence.meetingTeam.entity

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.util.*

@Embeddable
@Table(name = "meeting_member")
class MeetingMemberJpaEntity(
    id: UUID,
    userId: UUID,
    role: MeetingMemberRole,
) {

    @Column(nullable = false, updatable = false)
    var id: UUID = id
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
            userId = userId,
            role = role,
        )
    }

    companion object {

        fun MeetingMember.toJpaEntity(): MeetingMemberJpaEntity {
            return MeetingMemberJpaEntity(
                id = id,
                userId = userId,
                role = role,
            )
        }
    }

}
