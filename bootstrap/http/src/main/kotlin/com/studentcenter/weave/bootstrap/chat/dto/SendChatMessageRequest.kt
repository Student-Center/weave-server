package com.studentcenter.weave.bootstrap.chat.dto

import com.studentcenter.weave.domain.chat.entity.ChatMessage

data class SendChatMessageRequest(
    val contents: List<Content>,
) {

    data class Content(
        val type: ChatMessage.Content.ContentType?,
        val value: String?,
    ) {

        fun toDomainContent(): ChatMessage.Content {
            requireNotNull(type) { "메시지 타입을 입력해 주세요" }
            requireNotNull(value) { "메시지를 입력해 주세요" }

            return ChatMessage.Content(
                type = type,
                value = value
            )
        }
    }

}
