package com.studentcenter.weave.application.chat.outbound

import com.studentcenter.weave.application.chat.port.outbound.ChatRoomRepository
import com.studentcenter.weave.domain.chat.entity.ChatRoom
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class ChatRoomRepositorySpy: ChatRoomRepository {

    private val bucket = ConcurrentHashMap<UUID, ChatRoom>()

    override fun getById(id: UUID): ChatRoom {
        return bucket[id]!!
    }

    override fun save(chatRoom: ChatRoom) {
        bucket[chatRoom.id] = chatRoom
    }

    fun findAll(): List<ChatRoom> {
        return bucket.values.toList()
    }

    fun clear() {
        bucket.clear()
    }


}
