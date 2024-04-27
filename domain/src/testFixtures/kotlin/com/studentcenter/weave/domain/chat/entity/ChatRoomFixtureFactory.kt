package com.studentcenter.weave.domain.chat.entity

import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

object ChatRoomFixtureFactory {

    fun create(
        id: UUID = UuidCreator.create(),
        meetingId: UUID = UuidCreator.create(),
        requestingTeamId: UUID = UuidCreator.create(),
        receivingTeamId: UUID = UuidCreator.create(),
        createdAt: LocalDateTime = LocalDateTime.now(),
        members: Set<ChatMember> = emptySet(),
    ): ChatRoom {
        return ChatRoom(
            id = id,
            meetingId = meetingId,
            requestingTeamId = requestingTeamId,
            receivingTeamId = receivingTeamId,
            createdAt = createdAt,
            members = members
        )
    }

}
