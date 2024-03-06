package com.studentcenter.weave.bootstrap.common.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.studentcenter.weave.bootstrap.common.exception.ErrorResponse
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.security.jwt.exception.JwtExceptionType
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ExceptionHandlerFilter(
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
            if (e.type == JwtExceptionType.JWT_EXPIRED_EXCEPTION) {
                handleJwtExpiredException(response)
            } else {
                throw e
            }
        }
    }

    private fun handleJwtExpiredException(response: HttpServletResponse) {
        val errorResponse = ErrorResponse(
            exceptionCode = JwtExceptionType.JWT_EXPIRED_EXCEPTION.code,
            message = "토큰이 만료되었습니다.",
        )
        response.status = 400
        response.contentType = "application/json;charset=UTF-8"
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }

}
