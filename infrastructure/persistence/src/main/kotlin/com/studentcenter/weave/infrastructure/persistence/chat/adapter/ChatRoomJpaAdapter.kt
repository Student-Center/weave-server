package com.studentcenter.weave.infrastructure.persistence.chat.adapter

import com.studentcenter.weave.application.chat.port.outbound.ChatRoomRepository
import com.studentcenter.weave.domain.chat.entity.ChatRoom
import com.studentcenter.weave.infrastructure.persistence.chat.enitty.ChatRoomJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.chat.repository.ChatRoomJpaRepository
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceException
import org.springframework.stereotype.Component
import java.util.*

@Component
class ChatRoomJpaAdapter(
    private val chatRoomJpaRepository: ChatRoomJpaRepository,
) : ChatRoomRepository {

    override fun save(chatRoom: ChatRoom) {
        chatRoom
            .toJpaEntity()
            .also { chatRoomJpaRepository.save(it) }
    }

    override fun getById(id: UUID): ChatRoom {
        return chatRoomJpaRepository
            .findById(id)
            .orElseThrow { PersistenceException.ResourceNotFound("ChatRoom(id: $id)를 찾을 수 없습니다.") }
            .toDomain()
    }

}
