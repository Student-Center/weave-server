package com.studentcenter.weave.infrastructure.persistence.chat.enitty

import com.studentcenter.weave.domain.chat.entity.ChatMessage
import com.studentcenter.weave.infrastructure.persistence.chat.enitty.ChatMessageJpaEntity.ContentJpaEntity.Companion.toJpaEntity
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "chat_message")
class ChatMessageJpaEntity(
    id: UUID,
    roomId: UUID,
    senderId: UUID,
    senderType: ChatMessage.SenderType,
    contents: List<ContentJpaEntity>,
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "chat_message_content",
        joinColumns = [JoinColumn(name = "chat_message_id")]
    )
    var contents: List<ContentJpaEntity> = contents
        private set

    @Column(name = "created_at", updatable = false, nullable = false)
    var createdAt: LocalDateTime = createdAt
        private set

    @Embeddable
    @Table(name = "chat_message_content")
    class ContentJpaEntity(
        id: UUID,
        type: ChatMessage.Content.ContentType,
        value: String,
    ) {

        @Column(name = "id", updatable = false, nullable = false)
        var id: UUID = id
            private set

        @Column(
            name = "type",
            updatable = false,
            nullable = false,
            columnDefinition = "varchar(255)"
        )
        @Enumerated(EnumType.STRING)
        var type: ChatMessage.Content.ContentType = type
            private set

        @Column(name = "value", updatable = false, nullable = false)
        var value: String = value
            private set

        fun toDomain(): ChatMessage.Content {
            return ChatMessage.Content(
                id = id,
                type = type,
                value = value
            )
        }

        companion object {

            fun ChatMessage.Content.toJpaEntity(): ContentJpaEntity {
                return ContentJpaEntity(
                    id = id,
                    type = type,
                    value = value
                )
            }
        }

    }

    fun toDomain(): ChatMessage {
        return ChatMessage(
            id = id,
            roomId = roomId,
            senderId = senderId,
            senderType = senderType,
            contents = contents.map { it.toDomain() },
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
                contents = contents.map { it.toJpaEntity() },
                createdAt = createdAt
            )
        }
    }

}
