package com.studentcenter.weave.application.chat.port.inbound

import com.studentcenter.weave.domain.chat.entity.ChatMessage
import java.util.*

fun interface SendChatMessage {

    fun invoke(
        roomId: UUID,
        senderId: UUID,
        contents: List<ChatMessage.Content>,
    )

}
