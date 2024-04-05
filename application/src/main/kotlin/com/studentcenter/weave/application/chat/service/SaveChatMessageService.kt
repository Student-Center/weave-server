package com.studentcenter.weave.application.chat.service

import com.studentcenter.weave.application.chat.port.inbound.SaveChatMessage
import com.studentcenter.weave.domain.chat.entity.ChatMessage
import org.springframework.stereotype.Service

@Service
class SaveChatMessageService: SaveChatMessage {

    override fun handleChatMessage(chatMessage: ChatMessage) {
        // TODO : Save chat message to database
    }

}
