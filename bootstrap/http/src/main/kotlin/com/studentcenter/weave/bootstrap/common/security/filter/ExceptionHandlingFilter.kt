package com.studentcenter.weave.bootstrap.common.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.studentcenter.weave.bootstrap.common.exception.ErrorResponse
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.security.jwt.exception.JwtExceptionType
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class ExceptionHandlingFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: CustomException) {
            when(e.type) {
                JwtExceptionType.JWT_EXPIRED_EXCEPTION -> handleJwtExpiredException(response)
                else -> throw e
            }
        }
    }

    private fun handleJwtExpiredException(response: HttpServletResponse) {
        val errorResponse = ErrorResponse(
            exceptionCode = JwtExceptionType.JWT_EXPIRED_EXCEPTION.code,
            message = "토큰이 만료되었습니다.",
        )
        response.status = HttpStatus.BAD_REQUEST.value()
        response.contentType = "${MediaType.APPLICATION_JSON_VALUE};charset=${Charsets.UTF_8.name()}"
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }

}
