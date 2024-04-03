package com.studentcenter.weave.application.chat.outbound

import com.studentcenter.weave.application.chat.port.outbound.ChatRoomRepository
import com.studentcenter.weave.domain.chat.entity.ChatRoom
import java.util.*

class ChatRoomRepositorySpy: ChatRoomRepository {

    val bucket = mutableListOf<ChatRoom>()

    override fun getById(id: UUID): ChatRoom {
        return bucket.find { it.id == id }!!
    }

    fun save(chatRoom: ChatRoom) {
        bucket.add(chatRoom)
    }

    fun clear() {
        bucket.clear()
    }


}
