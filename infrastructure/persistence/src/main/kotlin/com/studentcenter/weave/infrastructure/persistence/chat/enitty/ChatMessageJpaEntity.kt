package com.studentcenter.weave.infrastructure.persistence.chat.enitty

import com.studentcenter.weave.domain.chat.entity.ChatMessage
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "chat_message")
class ChatMessageJpaEntity(
    id: UUID,
    roomId: UUID,
    senderId: UUID,
    senderType: ChatMessage.SenderType,
    contents: List<ChatMessage.Content>,
    createdAt: LocalDateTime,
) {

    @Id
    var id: UUID = id
        private set

    @Column(name = "room_id", updatable = false, nullable = false)
    var roomId: UUID = roomId
        private set

    @Column(name = "sender_id", updatable = false, nullable = false)
    var senderId: UUID = senderId
        private set

    @Enumerated(EnumType.STRING)
    @Column(
        name = "sender_type",
        updatable = false,
        nullable = false,
        columnDefinition = "varchar(255)"
    )
    var senderType: ChatMessage.SenderType = senderType
        private set

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "contents", updatable = false, nullable = false, columnDefinition = "json")
    var contents: List<ChatMessage.Content> = contents
        private set

    @Column(name = "created_at", updatable = false, nullable = false)
    var createdAt: LocalDateTime = createdAt
        private set

    fun toDomain(): ChatMessage {
        return ChatMessage(
            id = id,
            roomId = roomId,
            senderId = senderId,
            senderType = senderType,
            contents = contents,
            createdAt = createdAt
        )
    }

    companion object {

        fun ChatMessage.toJpaEntity(): ChatMessageJpaEntity {
            return ChatMessageJpaEntity(
                id = id,
                roomId = roomId,
                senderId = senderId,
                senderType = senderType,
                contents = contents,
                createdAt = createdAt
            )
        }
    }

}
