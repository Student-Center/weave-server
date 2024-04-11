package com.studentcenter.weave.application.chat.service

import com.studentcenter.weave.application.chat.port.inbound.SendChatMessage
import com.studentcenter.weave.application.chat.port.outbound.ChatMessagePublisher
import com.studentcenter.weave.domain.chat.entity.ChatMessage
import org.springframework.stereotype.Service
import java.util.*

@Service
class SendChatMessageService(
    private val chatMessagePublisher: ChatMessagePublisher,
) : SendChatMessage {

    override fun invoke(
        roomId: UUID,
        userId: UUID,
        contents: List<ChatMessage.Content>,
    ) {
        ChatMessage.createByUser(
            roomId = roomId,
            userId = userId,
            contents = contents,
        ).also {
            chatMessagePublisher.publish(it)
        }
    }

}
