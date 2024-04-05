package com.studentcenter.weave.application.chat.port.outbound

import com.studentcenter.weave.domain.chat.entity.ChatMessage

interface ChatMessagePublisher {

    fun publish(chatMessage: ChatMessage)

}
