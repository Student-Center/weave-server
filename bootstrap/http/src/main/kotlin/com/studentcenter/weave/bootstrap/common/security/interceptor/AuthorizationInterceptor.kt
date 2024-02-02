package com.studentcenter.weave.bootstrap.common.security.interceptor

import com.studentcenter.weave.application.common.security.vo.UserAuthentication
import com.studentcenter.weave.bootstrap.common.exception.ApiExceptionType
import com.studentcenter.weave.support.common.exception.CustomException
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
        handler: Any
    ): Boolean {
        if (hasSecuredAnnotation(handler) && isNotAuthenticated()) {
            throw CustomException(ApiExceptionType.UNAUTHORIZED, "요청 권한이 없습니다.")
        }

        return super.preHandle(request, response, handler)
    }

    private fun hasSecuredAnnotation(handler: Any): Boolean {
        (handler as HandlerMethod).method.declaredAnnotations.forEach {
            if (it is Secured) {
                return true
            }
        }
        return false
    }

    private fun isNotAuthenticated(): Boolean {
        SecurityContextHolder
            .getContext<UserAuthentication>()
            .let { userAuthentication ->
                return userAuthentication == null
            }
    }

}
