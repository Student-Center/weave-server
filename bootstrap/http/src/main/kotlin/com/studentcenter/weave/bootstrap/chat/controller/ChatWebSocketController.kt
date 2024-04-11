package com.studentcenter.weave.bootstrap.chat.controller

import com.studentcenter.weave.application.chat.port.inbound.SendChatMessage
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.bootstrap.chat.dto.SendChatMessageRequest
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import java.util.*

@Controller
class ChatWebSocketController(
    private val sendChatMessage: SendChatMessage,
) {

    @MessageMapping("/chat-rooms/{roomId}")
    fun sendChatMessage(
        @DestinationVariable roomId: UUID,
        @RequestBody request: SendChatMessageRequest,
        userAuthentication: UserAuthentication,
    ) {
        sendChatMessage.invoke(
            roomId = roomId,
            userId = userAuthentication.userId,
            contents = request.contents,
        )
    }

}
