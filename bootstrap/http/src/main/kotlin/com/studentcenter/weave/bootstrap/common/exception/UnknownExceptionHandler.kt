package com.studentcenter.weave.bootstrap.common.exception

import com.studentcenter.weave.support.common.exception.SystemExceptionType
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Hidden
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@Hidden
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE + 1)
class UnknownExceptionHandler {

    private val logger = KotlinLogging.logger { }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoHandlerFoundException(
        e: NoResourceFoundException,
        request: HttpServletRequest
    ): ErrorResponse {
        val requestMethod: String = request.method
        val requestUrl: String = request.requestURI
        val clientIp: String = request.getHeader("X-Forwarded-For") ?: request.remoteAddr

        logger.warn { "NoResourceFoundException: $requestMethod $requestUrl from $clientIp" }

        return ErrorResponse(
            exceptionCode = SystemExceptionType.NOT_FOUND.code,
            message = "Not Found"
        )
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable::class)
    fun handleApiException(
        e: Throwable,
        request: HttpServletRequest
    ): ErrorResponse {
        logger.error { e.stackTraceToString() }

        return ErrorResponse(
            exceptionCode = SystemExceptionType.INTERNAL_SERVER_ERROR.code,
            message = "Internal Server Error"
        )
    }
}
