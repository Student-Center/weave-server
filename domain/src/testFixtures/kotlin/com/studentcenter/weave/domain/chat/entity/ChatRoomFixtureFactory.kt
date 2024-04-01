package com.studentcenter.weave.domain.chat.entity

import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

object ChatRoomFixtureFactory {

    fun create(
        id: UUID = UuidCreator.create(),
        requestingTeamId: UUID = UuidCreator.create(),
        receivingTeamId: UUID = UuidCreator.create(),
        createdAt: LocalDateTime = LocalDateTime.now(),
    ): ChatRoom {
        return ChatRoom(
            id = id,
            requestingTeamId = requestingTeamId,
            receivingTeamId = receivingTeamId,
            createdAt = createdAt,
        )

    }
}
