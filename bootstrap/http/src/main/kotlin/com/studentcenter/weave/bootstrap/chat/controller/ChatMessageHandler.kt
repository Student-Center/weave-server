package com.studentcenter.weave.bootstrap.chat.controller

import com.studentcenter.weave.application.chat.port.inbound.SaveChatMessage
import com.studentcenter.weave.domain.chat.entity.ChatMessage
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller

@Controller
class ChatMessageHandler(
    private val simpMessageSendingOperations: SimpMessageSendingOperations,
    private val saveChatMessage: SaveChatMessage,
) {

    @EventListener
    fun handleChatMessage(chatMessage: ChatMessage) {
        simpMessageSendingOperations.convertAndSend(
            "/topic/chat-rooms/${chatMessage.roomId}",
            chatMessage
        )
        saveChatMessage.handleChatMessage(chatMessage)
    }

}
