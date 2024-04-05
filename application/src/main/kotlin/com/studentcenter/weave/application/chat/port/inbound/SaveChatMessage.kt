package com.studentcenter.weave.application.chat.port.inbound

import com.studentcenter.weave.domain.chat.entity.ChatMessage

interface SaveChatMessage {

    fun handleChatMessage(chatMessage: ChatMessage)

}
