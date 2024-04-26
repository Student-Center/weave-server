package com.studentcenter.weave.domain.chat.entity

import com.studentcenter.weave.domain.common.DomainEntity
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.util.*

data class ChatMember(
    override val id: UUID = UuidCreator.create(),
    val userId: UUID,
    val lastReadMessageId: UUID? = null,
) : DomainEntity {

    companion object {

        fun create(userId: UUID): ChatMember {
            return ChatMember(userId = userId)
        }

    }

}
