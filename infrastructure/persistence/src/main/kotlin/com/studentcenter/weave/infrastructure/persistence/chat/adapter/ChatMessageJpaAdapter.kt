package com.studentcenter.weave.infrastructure.persistence.chat.adapter

import com.studentcenter.weave.application.chat.port.inbound.GetChatMessage
import com.studentcenter.weave.application.chat.port.outbound.ChatMessageRepository
import com.studentcenter.weave.domain.chat.entity.ChatMessage
import com.studentcenter.weave.infrastructure.persistence.chat.enitty.ChatMessageJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.chat.repository.ChatMessageJpaRepository
import org.springframework.stereotype.Component

@Component
class ChatMessageJpaAdapter(
    private val chatMessageJpaRepository: ChatMessageJpaRepository,
) : ChatMessageRepository {

    override fun save(chatMessage: ChatMessage) {
        chatMessage
            .toJpaEntity()
            .also { chatMessageJpaRepository.save(it) }
    }

    override fun getScrollList(query: GetChatMessage.ScrollListQuery): List<ChatMessage> {
        return chatMessageJpaRepository
            .getScrollList(
                chatRoomId = query.chatRoomId,
                next = query.next,
                limit = query.limit,
            )
            .map { it.toDomain() }
    }

}
