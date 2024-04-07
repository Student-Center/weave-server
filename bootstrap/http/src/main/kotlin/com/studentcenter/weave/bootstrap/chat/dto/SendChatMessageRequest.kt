package com.studentcenter.weave.bootstrap.chat.dto

import com.studentcenter.weave.domain.chat.entity.ChatMessage

data class SendChatMessageRequest(
    val contents: List<ChatMessage.Content>,
)
