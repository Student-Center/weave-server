package com.studentcenter.weave.bootstrap.chat.controller

import com.studentcenter.weave.application.chat.port.inbound.GetChatMessage
import com.studentcenter.weave.bootstrap.chat.api.ChatMessageApi
import com.studentcenter.weave.bootstrap.chat.dto.GetChatMessagesRequest
import com.studentcenter.weave.bootstrap.chat.dto.GetChatMessagesResponse
import com.studentcenter.weave.bootstrap.chat.dto.GetChatMessagesResponse.Companion.toResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatMessageRestController(
    private val getChatMessage: GetChatMessage,
) : ChatMessageApi {

    override fun getChatMessages(request: GetChatMessagesRequest): GetChatMessagesResponse {
        val (chatRoomId, next, limit) = request.validate()
        return GetChatMessage
            .ScrollListQuery(chatRoomId, next, limit)
            .let { getChatMessage.getScrollList(it) }
            .toResponse()
    }

}
