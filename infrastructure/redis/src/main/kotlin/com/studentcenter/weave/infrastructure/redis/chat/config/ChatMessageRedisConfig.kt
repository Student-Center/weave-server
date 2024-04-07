package com.studentcenter.weave.infrastructure.redis.chat.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.studentcenter.weave.infrastructure.redis.chat.adapter.ChatMessageRedisConsumer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter

@Configuration
class ChatMessageRedisConfig {

    @Bean
    fun redisListenerContainer(
        connectionFactory: RedisConnectionFactory,
        messageListenerAdapter: MessageListenerAdapter,
    ): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(connectionFactory)
        container.addMessageListener(messageListenerAdapter, chatChannelTopic())
        return container
    }

    @Bean
    fun messageListenerAdapter(chatMessageRedisConsumer: ChatMessageRedisConsumer): MessageListenerAdapter {
        return MessageListenerAdapter(chatMessageRedisConsumer)
    }

    @Bean
    fun chatChannelTopic(): ChannelTopic {
        return ChannelTopic(CHAT_MESSAGE)
    }

    companion object {

        const val CHAT_MESSAGE = "chat_message"

    }
}
