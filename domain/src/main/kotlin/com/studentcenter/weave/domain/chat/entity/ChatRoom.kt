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
    val members: Set<ChatMember> = emptySet(),
) : AggregateRoot {

    fun addMember(userId: UUID): ChatRoom {
        require(members.none { it.userId == userId }) {
            "이미 채팅방에 참여하고 있는 사용자입니다."
        }

        val newMember: ChatMember = ChatMember.create(userId)
        return copy(members = members + newMember)
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
