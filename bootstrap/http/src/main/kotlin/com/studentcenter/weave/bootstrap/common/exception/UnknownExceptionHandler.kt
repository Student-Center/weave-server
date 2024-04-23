package com.studentcenter.weave.bootstrap.common.exception

import com.studentcenter.weave.support.common.exception.SystemException
import io.github.oshai.kotlinlogging.KotlinLogging
import io.sentry.Sentry
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
        request: HttpServletRequest,
    ): ErrorResponse {
        val requestMethod: String = request.method
        val requestUrl: String = request.requestURI
        val queryString: String = request.queryString?.let { "?$it" } ?: ""
        val clientIp: String = request.getHeader("X-Forwarded-For") ?: request.remoteAddr

        logger.warn { "NoResourceFoundException: $requestMethod $requestUrl${queryString} from $clientIp" }

        return ErrorResponse(
            exceptionCode = SystemException.NotFound().code,
            message = "Not Found"
        )
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable::class)
    fun handleApiException(
        e: Throwable,
        request: HttpServletRequest,
    ): ErrorResponse {
        logger.error { e.stackTraceToString() }

        Sentry.captureException(e) { scope ->
            scope.setExtra("method", request.method)
            scope.setExtra("requestURI", request.requestURI)
            scope.setExtra("queryString", request.queryString?.let { "?$it" } ?: "")
            scope.setExtra("clientIp", request.getHeader("X-Forwarded-For") ?: request.remoteAddr)
        }
        return ErrorResponse(
            exceptionCode = SystemException.InternalServerError().code,
            message = "Internal Server Error"
        )
    }
}
