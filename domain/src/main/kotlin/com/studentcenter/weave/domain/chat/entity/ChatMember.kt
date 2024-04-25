package com.studentcenter.weave.domain.chat.entity

import com.studentcenter.weave.domain.common.DomainEntity
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

data class ChatMember(
    override val id: UUID = UuidCreator.create(),
    val userId: UUID,
    val lastReadMessageId: UUID? = null,
    val lastReadAt: LocalDateTime? = null,
) : DomainEntity {

    companion object {

        fun create(userId: UUID, ): ChatMember {
            return ChatMember(userId = userId)
        }

    }

}
