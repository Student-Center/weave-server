package com.studentcenter.weave.infrastructure.persistence.chat.adapter

import com.studentcenter.weave.application.chat.port.outbound.ChatRoomRepository
import com.studentcenter.weave.domain.chat.entity.ChatRoom
import com.studentcenter.weave.infrastructure.persistence.chat.enitty.ChatRoomJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.chat.repository.ChatRoomJpaRepository
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceExceptionType
import com.studentcenter.weave.support.common.exception.CustomException
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
            .orElseThrow {
                CustomException(
                    type = PersistenceExceptionType.RESOURCE_NOT_FOUND,
                    message = "채팅방을 찾을 수 없습니다. id: $id"
                )
            }
            .toDomain()
    }

}
