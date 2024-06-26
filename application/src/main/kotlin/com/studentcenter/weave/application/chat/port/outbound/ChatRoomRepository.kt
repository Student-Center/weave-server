package com.studentcenter.weave.application.chat.port.outbound

import com.studentcenter.weave.domain.chat.entity.ChatRoom
import java.util.UUID

interface ChatRoomRepository {

    fun save(chatRoom: ChatRoom)

    fun getById(id: UUID): ChatRoom

}
