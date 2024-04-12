package com.studentcenter.weave.application.chat.service

import com.studentcenter.weave.application.chat.port.inbound.SaveChatMessage
import com.studentcenter.weave.application.chat.port.outbound.ChatMessageRepository
import com.studentcenter.weave.domain.chat.entity.ChatMessage
import org.springframework.stereotype.Service

@Service
class SaveChatMessageService(
    private val chatMessageRepository: ChatMessageRepository
): SaveChatMessage {

    override fun invoke(chatMessage: ChatMessage) {
        chatMessageRepository.save(chatMessage)
    }

}