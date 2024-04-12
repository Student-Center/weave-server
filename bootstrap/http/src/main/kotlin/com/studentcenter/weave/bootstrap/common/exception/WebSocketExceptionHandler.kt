package com.studentcenter.weave.bootstrap.common.exception

import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class WebSocketExceptionHandler {

    @MessageExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(exception: IllegalArgumentException): ErrorResponse {
        return ErrorResponse(
            exceptionCode = ApiExceptionType.INVALID_PARAMETER.code,
            message = exception.message ?: "Bad request"
        )
    }

    @MessageExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ErrorResponse {
        return ErrorResponse(
            exceptionCode = exception.type.code,
            message = exception.message
        )
    }

    @MessageExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ErrorResponse {
        return ErrorResponse(
            exceptionCode = "INTERNAL_SERVER_ERROR",
            message = exception.message ?: "Internal server error"
        )
    }

}
