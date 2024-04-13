package com.studentcenter.weave.bootstrap.chat.dto

import com.studentcenter.weave.application.chat.port.inbound.GetChatMessage
import com.studentcenter.weave.domain.chat.entity.ChatMessage
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "채팅 메시지 목록 조회 응답")
data class GetChatMessagesResponse(
    @Schema(description = "채팅 메시지 목록")
    val items: List<ChatMessage>,
    @Schema(description = "다음 메시지 ID")
    val next: UUID?,
    @Schema(description = "조회한 메시지 수")
    val total: Int,
) {
    companion object {
        fun GetChatMessage.ScrollListResult.toResponse(): GetChatMessagesResponse {
            return GetChatMessagesResponse(
                items = this.items,
                next = this.next,
                total = this.total,
            )
        }
    }
}
