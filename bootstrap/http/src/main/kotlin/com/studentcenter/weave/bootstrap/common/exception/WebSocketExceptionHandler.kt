package com.studentcenter.weave.bootstrap.common.exception

import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.exception.SystemException
import io.sentry.Sentry
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class WebSocketExceptionHandler {

    @MessageExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(exception: IllegalArgumentException): ErrorResponse {
        return ErrorResponse(
            exceptionCode = ApiException.InvalidParameter().code,
            message = exception.message ?: "Bad request"
        )
    }

    @MessageExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ErrorResponse {
        return ErrorResponse(
            exceptionCode = exception.code,
            message = exception.message
        )
    }

    @MessageExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ErrorResponse {
        Sentry.captureException(exception)
        return ErrorResponse(
            exceptionCode = SystemException.InternalServerError().code,
            message = exception.message ?: "Internal server error"
        )
    }

}
