package com.studentcenter.weave.bootstrap.chat.api

import com.studentcenter.weave.bootstrap.chat.dto.ChatRoomDetailResponse
import com.studentcenter.weave.bootstrap.common.security.annotation.Secured
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@Tag(name = "ChatRoom", description = "채팅방 API")
@RequestMapping("/api/chat-rooms")
interface ChatRoomApi {

    @Secured
    @Operation(summary = "채팅방 정보 상세 조회")
    @GetMapping("/{chatRoomId}")
    @ResponseStatus(HttpStatus.OK)
    fun getChatRoomDetail(@PathVariable chatRoomId: UUID): ChatRoomDetailResponse

}
