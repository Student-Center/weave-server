package com.studentcenter.weave.bootstrap.chat.api

import com.studentcenter.weave.bootstrap.chat.dto.GetChatMessagesRequest
import com.studentcenter.weave.bootstrap.chat.dto.GetChatMessagesResponse
import com.studentcenter.weave.bootstrap.common.security.annotation.Secured
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "ChatMessage", description = "채팅 메시지 API")
@RequestMapping("/api/chat-messages")
interface ChatMessageApi {

    @Secured
    @Operation(summary = "채팅 메시지 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getChatMessages(request: GetChatMessagesRequest): GetChatMessagesResponse

}
