package com.studentcenter.weave.bootstrap.common.exception

import com.studentcenter.weave.support.common.exception.CustomException
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.DateTimeException

@Hidden
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class CustomExceptionHandler {

    private val logger = KotlinLogging.logger { }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException::class)
    fun handleApiException(exception: CustomException): ErrorResponse {
        return ErrorResponse(
            exceptionCode = exception.type.code,
            message = exception.message,
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeException::class)
    fun handleDateTimeException(exception: DateTimeException): ErrorResponse {
        return ErrorResponse(
            exceptionCode = ApiExceptionType.INVALID_DATE_EXCEPTION.code,
            message = exception.message!!,
        )
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpMethodNotSupportedException(exception: HttpRequestMethodNotSupportedException): ErrorResponse {
        return ErrorResponse(
            exceptionCode = ApiExceptionType.INVALID_PARAMETER.code,
            message = "지원하지 않는 Http Method 입니다.",
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMethodNotReadableException(exception: HttpMessageNotReadableException): ErrorResponse {
        return ErrorResponse(
            exceptionCode = ApiExceptionType.INVALID_PARAMETER.code,
            message = "잘못된 HttpBody 형식입니다.",
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleRequestValidException(exception: MethodArgumentNotValidException): ErrorResponse {
        val builder = StringBuilder()
        exception
            .bindingResult
            .fieldErrors
            .forEach { fieldError ->
            builder
                .append("[${fieldError.field}](은)는 ${fieldError.defaultMessage}")
                .append("입력된 값: ${fieldError.rejectedValue}")
                .append("|")
        }
        val message = builder.toString()
        return ErrorResponse(
            exceptionCode = ApiExceptionType.INVALID_PARAMETER.code,
            message = message.substring(0, message.lastIndexOf("|")),
        )
    }

}
