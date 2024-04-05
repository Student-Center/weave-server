package com.studentcenter.weave.bootstrap.chat.controller

import com.studentcenter.weave.application.chat.port.inbound.SendChatMessage
import com.studentcenter.weave.bootstrap.chat.dto.SendChatMessageRequest
import com.studentcenter.weave.support.common.uuid.UuidCreator
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class ChatWebSocketController(
    private val sendChatMessage: SendChatMessage,
) {

    @MessageMapping("/chat-rooms/{roomId}")
    @SendTo("/topic/chat-rooms/{roomId}")
    fun sendTextMessage(
        @DestinationVariable roomId: UUID,
        request: SendChatMessageRequest,
    ) {
        sendChatMessage.invoke(
            roomId = roomId,
            senderId = UuidCreator.create(), // TODO: 토큰 인증 구현
            contents = request.contents,
        )
    }

}
