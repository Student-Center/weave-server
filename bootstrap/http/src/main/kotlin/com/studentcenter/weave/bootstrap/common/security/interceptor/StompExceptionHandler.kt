package com.studentcenter.weave.bootstrap.common.security.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import com.studentcenter.weave.bootstrap.common.exception.ErrorResponse
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.exception.SystemExceptionType
import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler

@Component
class StompExceptionHandler(
    private val objectMapper: ObjectMapper,
) : StompSubProtocolErrorHandler() {

    override fun handleClientMessageProcessingError(
        clientMessage: Message<ByteArray>?,
        ex: Throwable,
    ): Message<ByteArray>? {
        return when (val cause: Throwable? = ex.cause) {
            is CustomException -> handleCustomException(cause)
            else -> handleException(ex)
        }
    }

    private fun handleCustomException(customException: CustomException): Message<ByteArray> {
        val response = ErrorResponse(
            customException.type.code,
            customException.message
        )
        val accessor = StompHeaderAccessor.create(StompCommand.ERROR)
        accessor.setLeaveMutable(true)
        return MessageBuilder.createMessage(
            objectMapper.writeValueAsBytes(response),
            accessor.messageHeaders
        )
    }

    private fun handleException(ex: Throwable): Message<ByteArray> {
        val response = ErrorResponse(
            SystemExceptionType.INTERNAL_SERVER_ERROR.code,
            ex.message.toString(),
        )
        val accessor = StompHeaderAccessor.create(StompCommand.ERROR)
        accessor.setLeaveMutable(true)
        return MessageBuilder.createMessage(
            objectMapper.writeValueAsBytes(response),
            accessor.messageHeaders
        )
    }

}
