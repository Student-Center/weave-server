package com.studentcenter.weave.application.chat.port.inbound

import com.studentcenter.weave.domain.chat.entity.ChatMessage
import com.studentcenter.weave.support.common.dto.ScrollRequest
import com.studentcenter.weave.support.common.dto.ScrollResponse
import java.util.UUID

interface GetChatMessage {

    fun getScrollList(query: ScrollListQuery): ScrollListResult

    data class ScrollListQuery(
        val chatRoomId: UUID,
        override val next: UUID?,
        override val limit: Int
    ) : ScrollRequest<UUID?>(
        next = next,
        limit = limit
    )

    data class ScrollListResult(
        override val items: List<ChatMessage>,
        override val next: UUID?,
    ) : ScrollResponse<ChatMessage, UUID?>(
        items = items,
        next = next,
        total = items.size,
    )

}
