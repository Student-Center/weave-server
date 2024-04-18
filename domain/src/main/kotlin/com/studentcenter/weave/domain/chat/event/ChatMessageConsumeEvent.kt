package com.studentcenter.weave.domain.chat.event

import com.studentcenter.weave.domain.chat.entity.ChatMessage
import com.studentcenter.weave.domain.common.DomainEvent

data class ChatMessageConsumeEvent(
    override val entity: ChatMessage,
) : DomainEvent<ChatMessage> {

    companion object {

        fun from(chatMessage: ChatMessage): ChatMessageConsumeEvent {
            return ChatMessageConsumeEvent(chatMessage)
        }

    }

}
