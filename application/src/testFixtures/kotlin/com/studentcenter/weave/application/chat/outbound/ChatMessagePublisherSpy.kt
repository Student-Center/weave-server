package com.studentcenter.weave.application.chat.outbound

import com.studentcenter.weave.application.chat.port.outbound.ChatMessagePublisher
import com.studentcenter.weave.domain.chat.entity.ChatMessage

class ChatMessagePublisherSpy: ChatMessagePublisher {

    private val bucket = mutableListOf<ChatMessage>()

    override fun publish(chatMessage: ChatMessage) {
        bucket.add(chatMessage)
    }

    fun count(): Int = bucket.size

}
