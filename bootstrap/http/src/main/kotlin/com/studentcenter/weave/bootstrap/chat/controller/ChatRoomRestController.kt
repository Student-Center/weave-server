package com.studentcenter.weave.bootstrap.chat.controller

import com.studentcenter.weave.application.chat.port.inbound.GetChatRoom
import com.studentcenter.weave.bootstrap.chat.api.ChatRoomApi
import com.studentcenter.weave.bootstrap.chat.dto.ChatRoomDetailResponse
import com.studentcenter.weave.bootstrap.chat.dto.ChatRoomDetailResponse.Companion.toResponse
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ChatRoomRestController(
    private val getChatRoom: GetChatRoom,
) : ChatRoomApi {

    override fun getChatRoomDetail(chatRoomId: UUID): ChatRoomDetailResponse {
        return getChatRoom
            .getDetailById(chatRoomId)
            .toResponse()
    }
}
