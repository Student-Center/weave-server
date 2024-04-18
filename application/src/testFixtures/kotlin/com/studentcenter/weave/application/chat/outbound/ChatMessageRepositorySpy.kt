package com.studentcenter.weave.application.chat.outbound

import com.studentcenter.weave.application.chat.port.inbound.GetChatMessage
import com.studentcenter.weave.application.chat.port.outbound.ChatMessageRepository
import com.studentcenter.weave.domain.chat.entity.ChatMessage
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class ChatMessageRepositorySpy: ChatMessageRepository {

    private val bucket = ConcurrentHashMap<UUID, ChatMessage>()

    override fun save(chatMessage: ChatMessage) {
        bucket[chatMessage.id] = chatMessage
    }

    override fun getScrollList(query: GetChatMessage.ScrollListQuery): List<ChatMessage> {
        val next = query.next
        val limit = query.limit

        return bucket.values
            .filter { it.id > next }
            .filter { it.roomId == query.chatRoomId }
            .sortedBy { it.id }
            .take(limit)
    }

    fun findAllByUserIdAndRoomId(userId: UUID, roomId: UUID): List<ChatMessage> {
        return bucket.values
            .filter { it.senderId == userId }
            .filter { it.roomId == roomId }
    }

    fun clear() {
        bucket.clear()
    }

}
