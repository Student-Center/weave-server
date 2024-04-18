package com.studentcenter.weave.infrastructure.redis.chat.adapter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.studentcenter.weave.domain.chat.entity.ChatMessage
import com.studentcenter.weave.domain.chat.event.ChatMessageConsumeEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class ChatMessageRedisConsumer(
    private val objectMapper: ObjectMapper,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : MessageListener {

    val logger = KotlinLogging.logger { this::class.java.name }

    override fun onMessage(
        message: Message,
        pattern: ByteArray?,
    ) {
        logger.debug { "Chat Message Received: ${message.body}" }

        objectMapper
            .readValue<ChatMessage>(message.body)
            .let { ChatMessageConsumeEvent.from(it) }
            .also { applicationEventPublisher.publishEvent(it) }
    }

}
