package com.studentcenter.weave.bootstrap.common.security.interceptor

import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.bootstrap.common.exception.ApiException
import com.studentcenter.weave.bootstrap.common.security.annotation.Secured
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthorizationInterceptor : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        if (handler !is HandlerMethod) {
            return super.preHandle(request, response, handler)
        }

        if (hasSecuredAnnotation(handler) && isNotAuthenticated()) {
            throw ApiException.UnauthorizedRequest(UNAUTHORIZED_REQUEST_MESSAGE)
        }

        return super.preHandle(request, response, handler)
    }

    private fun hasSecuredAnnotation(handler: Any): Boolean {
        return (handler as HandlerMethod).hasMethodAnnotation(Secured::class.java)
    }

    private fun isNotAuthenticated(): Boolean {
        SecurityContextHolder
            .getContext<UserAuthentication>()
            .let { userAuthentication ->
                return userAuthentication == null
            }
    }

    companion object {

        const val UNAUTHORIZED_REQUEST_MESSAGE = "인증되지 않은 사용자 입니다."
    }

}
