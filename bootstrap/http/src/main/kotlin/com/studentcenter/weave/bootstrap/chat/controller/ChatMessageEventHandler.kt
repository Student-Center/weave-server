package com.studentcenter.weave.bootstrap.chat.controller

import com.studentcenter.weave.application.chat.port.inbound.SaveChatMessage
import com.studentcenter.weave.domain.chat.entity.ChatMessage
import com.studentcenter.weave.domain.chat.event.ChatMessageConsumeEvent
import com.studentcenter.weave.support.common.functions.invokeOnFailure
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller

@Controller
class ChatMessageEventHandler(
    private val simpMessageSendingOperations: SimpMessageSendingOperations,
    private val saveChatMessage: SaveChatMessage,
) {

    private val logger = KotlinLogging.logger { this::class.java.name }

    @EventListener
    fun handleChatMessageConsumeEvent(event: ChatMessageConsumeEvent) {
        val chatMessage: ChatMessage = event.entity
        CoroutineScope(Dispatchers.IO).launch {
            async { broadCastChatMessage(chatMessage) }
                .invokeOnFailure { logger.error(it) { "Failed to broadcast chat message" } }

            async { saveChatMessage.invoke(chatMessage) }
                .invokeOnFailure { logger.error(it) { "Failed to save chat message" } }
        }
    }

    private fun broadCastChatMessage(chatMessage: ChatMessage) {
        simpMessageSendingOperations.convertAndSend(
            /* destination = */ "/topic/chat-rooms/${chatMessage.roomId}",
            /* payload = */ chatMessage,
        )
    }

}
