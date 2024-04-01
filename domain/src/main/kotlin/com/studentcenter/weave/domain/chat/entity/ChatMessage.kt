package com.studentcenter.weave.domain.chat.entity

import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

abstract class ChatMessage(
    open val id: UUID = UuidCreator.create(),
    open val roomId: UUID,
    open val createdAt: LocalDateTime = LocalDateTime.now(),
)