package com.studentcenter.weave.application.chat.port.outbound

import com.studentcenter.weave.application.chat.port.inbound.GetChatMessage
import com.studentcenter.weave.domain.chat.entity.ChatMessage

interface ChatMessageRepository {

    fun save(chatMessage: ChatMessage)

    fun getScrollList(query: GetChatMessage.ScrollListQuery): List<ChatMessage>

}
