package com.studentcenter.weave.domain.meeting.entity

import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

object MeetingFixtureFactory {

    fun create(
        id: UUID = UuidCreator.create(),
        requestingTeamId: UUID = UuidCreator.create(),
        receivingTeamId: UUID = UuidCreator.create(),
        status: MeetingStatus = MeetingStatus.PENDING,
        createdAt: LocalDateTime = LocalDateTime.now(),
        finishedAt: LocalDateTime? = null,
    ): Meeting {
        return Meeting(
            id = id,
            requestingTeamId = requestingTeamId,
            receivingTeamId = receivingTeamId,
            status = status,
            createdAt = createdAt,
            finishedAt = finishedAt,
        )
    }
}
