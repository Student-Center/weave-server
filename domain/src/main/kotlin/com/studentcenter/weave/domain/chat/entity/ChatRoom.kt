package com.studentcenter.weave.domain.chat.entity

import com.studentcenter.weave.domain.common.AggregateRoot
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

data class ChatRoom(
    override val id: UUID = UuidCreator.create(),
    val meetingId: UUID,
    val receivingTeamId: UUID,
    val requestingTeamId: UUID,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val members: List<ChatMember> = emptyList(),
) : AggregateRoot {

    fun addMember(
        userId: UUID,
        meetingId: UUID,
    ): ChatRoom {
        val existingMember: ChatMember? = members.find { it.userId == userId }
        return if (existingMember != null) {
            this
        } else {
            val newMember: ChatMember = ChatMember.create(userId, meetingId)
            this.copy(members = members + newMember)
        }
    }

    companion object {

        fun create(meeting: Meeting): ChatRoom {
            require(meeting.isCompleted()) { "미팅이 매칭되지 않았습니다." }

            return ChatRoom(
                meetingId = meeting.id,
                receivingTeamId = meeting.receivingTeamId,
                requestingTeamId = meeting.requestingTeamId
            )
        }

    }

}
