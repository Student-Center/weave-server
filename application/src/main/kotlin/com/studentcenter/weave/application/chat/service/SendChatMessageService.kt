package com.studentcenter.weave.application.chat.service

import com.studentcenter.weave.application.chat.port.inbound.SendChatMessage
import com.studentcenter.weave.application.chat.port.outbound.ChatMessagePublisher
import com.studentcenter.weave.application.chat.port.outbound.ChatMessageRepository
import com.studentcenter.weave.domain.chat.entity.ChatMessage
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        val chatMessage = ChatMessage.createByUser(roomId, userId, contents)

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            launch { chatMessagePublisher.publish(chatMessage) }
            launch { chatMessageRepository.save(chatMessage) }
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        logger.error(exception) { "Failed to process chat message" }
    }

}
