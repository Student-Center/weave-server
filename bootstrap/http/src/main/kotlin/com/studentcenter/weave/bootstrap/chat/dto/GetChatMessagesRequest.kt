package com.studentcenter.weave.bootstrap.chat.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "채팅 메시지 조회 요청")
data class GetChatMessagesRequest(
    @Schema(description = "채팅방 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    val chatRoomId: UUID?,
    @Schema(description = "이전 메시지 조회를 위한 cursor", example = "123e4567-e89b-12d3-a456-426614174000")
    val next: UUID?,
    @Schema(description = "한번에 조회할 메시지 수", defaultValue = "20", example = "20")
    val limit: Int? = 20,
) {

    fun validate(): Validated {
        requireNotNull(chatRoomId) { "유효하지 않은 채팅방 입니다" }
        requireNotNull(limit) { "유효하지 않은 요청입니다"}
        require(limit > 0) { "한번에 조회할 메시지 수는 0보다 커야 합니다" }
        return Validated(chatRoomId, next, limit)
    }

    data class Validated(
        val chatRoomId: UUID,
        val next: UUID?,
        val limit: Int,
    )

}
