package com.studentcenter.weave.infrastructure.persistence.chat.adapter

import com.studentcenter.weave.application.chat.port.outbound.ChatRoomRepository
import com.studentcenter.weave.domain.chat.entity.ChatRoom
import org.springframework.stereotype.Component
import java.util.*

@Component
class ChatRoomJpaAdapter: ChatRoomRepository {

    override fun getById(id: UUID): ChatRoom {
        TODO("Not yet implemented")
    }

}
