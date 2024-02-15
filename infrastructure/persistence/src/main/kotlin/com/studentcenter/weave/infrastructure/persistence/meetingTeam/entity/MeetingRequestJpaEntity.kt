package com.studentcenter.weave.infrastructure.persistence.meeting.entity

import com.studentcenter.weave.domain.meeting.entity.MeetingRequest
import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.enums.MeetingRequestStatus
import com.studentcenter.weave.domain.meeting.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.enums.Gender
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "meeting_request")
class MeetingRequestJpaEntity(
    id: UUID,
    requestingMeetingTeamId: UUID,
    receivingMeetingTeamId: UUID,
    status: MeetingRequestStatus,
    createdAt: LocalDateTime,
    respondAt: LocalDateTime?,
) {

    @Id
    @Column(nullable = false)
    var id: UUID = id
        private set

    @Column(nullable = false, updatable = false)
    var requestingMeetingTeamId: UUID = requestingMeetingTeamId
        private set

    @Column(nullable = false, updatable = false)
    var receivingMeetingTeamId: UUID = receivingMeetingTeamId
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    var status: MeetingRequestStatus = status
        private set

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = createdAt
        private set

    @Column()
    var respondAt: LocalDateTime? = respondAt
        private set

    fun toDomain(): MeetingRequest {
        return MeetingRequest(
            id = id,
            requestingMeetingTeamId = requestingMeetingTeamId,
            receivingMeetingTeamId = receivingMeetingTeamId,
            status = status,
            createdAt = createdAt,
            respondAt = respondAt,
        )
    }

    companion object {

        fun MeetingRequest.toJpaEntity(): MeetingRequestJpaEntity {
            return MeetingRequestJpaEntity(
                id = id,
                requestingMeetingTeamId = requestingMeetingTeamId,
                receivingMeetingTeamId = receivingMeetingTeamId,
                status = status,
                createdAt = createdAt,
                respondAt = respondAt,
            )
        }

    }

}
