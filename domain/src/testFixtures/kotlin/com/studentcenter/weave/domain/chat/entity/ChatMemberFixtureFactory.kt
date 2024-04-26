package com.studentcenter.weave.domain.chat.entity

import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

object ChatMemberFixtureFactory {

    fun create(
        id: UUID = UuidCreator.create(),
        userId: UUID = UuidCreator.create(),
        lastReadMessageId: UUID? = null,
    ): ChatMember {
        return ChatMember(
            id = id,
            userId = userId,
            lastReadMessageId = lastReadMessageId,
        )
    }

}
