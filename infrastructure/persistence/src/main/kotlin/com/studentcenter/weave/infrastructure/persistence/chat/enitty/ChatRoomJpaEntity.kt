package com.studentcenter.weave.infrastructure.persistence.chat.enitty

import com.studentcenter.weave.domain.chat.entity.ChatRoom
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "chat_room")
class ChatRoomJpaEntity(
    id: UUID,
    meetingId: UUID,
    receivingTeamId: UUID,
    requestingTeamId: UUID,
    createdAt: LocalDateTime,
) {

    @Id
    var id: UUID = id
        private set

    @Column(nullable = false, updatable = false)
    var meetingId: UUID = meetingId
        private set

    @Column(nullable = false, updatable = false)
    var receivingTeamId: UUID = receivingTeamId
        private set

    @Column(nullable = false, updatable = false)
    var requestingTeamId: UUID = requestingTeamId
        private set

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = createdAt
        private set

    fun toDomain(): ChatRoom {
        return ChatRoom(
            id = id,
            meetingId = meetingId,
            receivingTeamId = receivingTeamId,
            requestingTeamId = requestingTeamId,
            createdAt = createdAt,
        )
    }

    companion object {
        fun ChatRoom.toJpaEntity(
            meetingId: UUID,
            receivingTeamId: UUID,
            requestingTeamId: UUID,
        ): ChatRoomJpaEntity {
            return ChatRoomJpaEntity(
                id = UUID.randomUUID(),
                meetingId = meetingId,
                receivingTeamId = receivingTeamId,
                requestingTeamId = requestingTeamId,
                createdAt = LocalDateTime.now(),
            )
        }
    }

}
