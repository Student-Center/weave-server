package com.studentcenter.weave.application.chat.service

import com.studentcenter.weave.application.chat.port.inbound.SendChatMessage
import com.studentcenter.weave.application.chat.port.outbound.ChatMessagePublisher
import com.studentcenter.weave.application.chat.port.outbound.ChatMessageRepository
import com.studentcenter.weave.domain.chat.entity.ChatMessage
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import java.util.*

@Service
class SendChatMessageService(
    private val chatMessagePublisher: ChatMessagePublisher,
    private val chatMessageRepository: ChatMessageRepository,
) : SendChatMessage {

    private val logger = KotlinLogging.logger { }

    override fun invoke(
        roomId: UUID,
        userId: UUID,
        contents: List<ChatMessage.Content>,
    ) {
        val chatMessage: ChatMessage = ChatMessage.createByUser(
            roomId = roomId,
            userId = userId,
            contents = contents,
        )

        CoroutineScope(Dispatchers.IO).launch {
            publishMessageAsync(chatMessage)
            saveMessageAsync(chatMessage)
        }
    }

    private fun CoroutineScope.publishMessageAsync(chatMessage: ChatMessage) =
        async { chatMessagePublisher.publish(chatMessage) }
            .invokeOnCompletion { if (it != null) logger.error(it) { "Failed to publish chat message" } }

    private fun CoroutineScope.saveMessageAsync(chatMessage: ChatMessage) =
        async { chatMessageRepository.save(chatMessage) }
            .invokeOnCompletion { if (it != null) logger.error(it) { "Failed to save chat message" } }

}
