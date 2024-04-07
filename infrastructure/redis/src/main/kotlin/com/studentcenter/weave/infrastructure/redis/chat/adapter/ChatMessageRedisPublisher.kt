package com.studentcenter.weave.infrastructure.redis.chat.adapter

import com.studentcenter.weave.application.chat.port.outbound.ChatMessagePublisher
import com.studentcenter.weave.domain.chat.entity.ChatMessage
import com.studentcenter.weave.infrastructure.redis.chat.config.ChatMessageRedisConfig
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class ChatMessageRedisPublisher(
    private val redisTemplate: RedisTemplate<String, Any>,
) : ChatMessagePublisher {

    override fun publish(chatMessage: ChatMessage) {
        redisTemplate.convertAndSend(ChatMessageRedisConfig.CHAT_MESSAGE, chatMessage)
    }

}
