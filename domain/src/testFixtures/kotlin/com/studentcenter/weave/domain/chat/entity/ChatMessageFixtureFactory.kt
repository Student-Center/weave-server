package com.studentcenter.weave.domain.chat.entity

import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.util.UUID

object ChatMessageFixtureFactory {

    fun create(
        id: UUID = UuidCreator.create(),
        roomId: UUID = UuidCreator.create(),
        senderId: UUID = UuidCreator.create(),
        senderType: ChatMessage.SenderType = ChatMessage.SenderType.USER,
        contents: List<ChatMessage.Content> = listOf(
            ChatMessage.Content(
                type = ChatMessage.Content.ContentType.TEXT,
                value = "Hello, World!"
            )
        ),
    ): ChatMessage {
        return ChatMessage(
            id = id,
            roomId = roomId,
            senderId = senderId,
            senderType = senderType,
            contents = contents,
        )
    }

}
