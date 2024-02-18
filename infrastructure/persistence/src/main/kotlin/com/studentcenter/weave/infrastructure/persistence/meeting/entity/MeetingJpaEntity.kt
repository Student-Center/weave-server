package com.studentcenter.weave.infrastructure.persistence.meeting.entity

import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "meeting")
class MeetingJpaEntity(
    id: UUID,
    requestingTeamId: UUID,
    receivingTeamId: UUID,
    status: MeetingStatus,
    createdAt: LocalDateTime,
    finishedAt: LocalDateTime?,
) {

    @Id
    var id: UUID = id
        private set

    @Column(nullable = false, updatable = false)
    var requestingTeamId = requestingTeamId
        private set

    @Column(nullable = false, updatable = false)
    var receivingTeamId = receivingTeamId
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    var status = status
        private set

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = createdAt
        private set

    @Column
    var finishedAt: LocalDateTime? = finishedAt
        private set

    fun toDomain(): Meeting {
        return Meeting(
            id = id,
            requestingTeamId = requestingTeamId,
            receivingTeamId = receivingTeamId,
            status = status,
            createdAt = createdAt,
            finishedAt = finishedAt,
        )
    }

    companion object {
        fun Meeting.toJpaEntity(): MeetingJpaEntity {
            return MeetingJpaEntity(
                id = id,
                requestingTeamId = requestingTeamId,
                receivingTeamId = receivingTeamId,
                status = status,
                createdAt = createdAt,
                finishedAt = finishedAt,
            )
        }
    }

}
