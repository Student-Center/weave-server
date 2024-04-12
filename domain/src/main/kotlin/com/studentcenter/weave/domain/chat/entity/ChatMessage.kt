package com.studentcenter.weave.domain.chat.entity

import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

data class ChatMessage(
    val id: UUID = UuidCreator.create(),
    val roomId: UUID,
    val senderId: UUID,
    val senderType: SenderType,
    val contents: List<Content>,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

    init {
        require(contents.isNotEmpty()) { "메시지를 입력해 주세요" }
    }

    enum class SenderType {
        USER,
    }

    data class Content(
        val type: ContentType,
        val value: String,
    ) {

        init {
            type.validation(value)
        }

        enum class ContentType(val validation: (String) -> Unit) {
            TEXT({ value: String -> require(value.length <= 1000) { "1000자 이내로 입력해 주세요" } }),
        }
    }

    companion object {

        fun createByUser(
            roomId: UUID,
            userId: UUID,
            contents: List<Content>,
        ): ChatMessage {
            return ChatMessage(
                roomId = roomId,
                senderId = userId,
                senderType = SenderType.USER,
                contents = contents
            )
        }
    }

}
