package com.studentcenter.weave.domain.chat.entity

import java.util.*

data class UserTextMessage(
    override val roomId: UUID,
    override val sender: SendUser,
    val content: String,
) : ChatMessage(
    sender = sender,
    roomId = roomId,
) {

    data class SendUser(val userId: UUID) : Sender

    init {
        require(content.isNotBlank()) { "내용을 입력해 주세요!" }
        require(content.length <= 1000) { "1000자 이내로 입력해 주세요!" }
    }

    companion object {

        fun create(
            roomId: UUID,
            sendUserId: UUID,
            content: String,
        ): UserTextMessage {
            return UserTextMessage(
                roomId = roomId,
                sender = SendUser(userId = sendUserId),
                content = content,
            )
        }
    }


}
