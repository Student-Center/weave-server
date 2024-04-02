package com.studentcenter.weave.domain.chat.entity

import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

data class ChatRoom(
    val id: UUID = UuidCreator.create(),
    val meetingId: UUID,
    val receivingTeamId: UUID,
    val requestingTeamId: UUID,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

    fun sendUserTextMessage(
        sendUserId: UUID,
        content: String,
    ): UserTextMessage {
        return UserTextMessage.create(
            roomId = id,
            sendUserId = sendUserId,
            content = content,
        )
    }

    companion object {

        fun create(meeting: Meeting): ChatRoom {
            require(meeting.isCompleted()) { "미팅이 매칭되지 않았습니다."}

            return ChatRoom(
                meetingId = meeting.id,
                receivingTeamId = meeting.receivingTeamId,
                requestingTeamId = meeting.requestingTeamId
            )
        }

    }

}
