package com.studentcenter.weave.application.chat.service

import com.studentcenter.weave.application.chat.port.inbound.GetChatMessage
import com.studentcenter.weave.application.chat.port.outbound.ChatMessageRepository
import com.studentcenter.weave.domain.chat.entity.ChatMessage
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetChatMessageService(
    private val chatMessageRepository: ChatMessageRepository,
) : GetChatMessage {

    override fun getScrollList(query: GetChatMessage.ScrollListQuery): GetChatMessage.ScrollListResult {
        val result: List<ChatMessage> = query
            .copy(limit = query.limit + 1)
            .let { chatMessageRepository.getScrollList(it) }

        return GetChatMessage.ScrollListResult(
            items = result.take(query.limit),
            next = if (result.size > query.limit) result.last().id else null
        )
    }

}
