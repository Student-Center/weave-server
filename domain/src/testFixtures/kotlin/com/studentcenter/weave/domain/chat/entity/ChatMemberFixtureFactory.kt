package com.studentcenter.weave.domain.chat.entity

import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

object ChatMemberFixtureFactory {

    fun create(
        chatRoomId: UUID = UuidCreator.create(),
        userId: UUID = UuidCreator.create(),
        lastReadMessageId: UUID? = null,
        lastReadAt: LocalDateTime? = null,
    ): ChatMember {
        return ChatMember(
            chatRoomId = chatRoomId,
            userId = userId,
            lastReadMessageId = lastReadMessageId,
            lastReadAt = lastReadAt
        )
    }
}
