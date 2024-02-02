package com.studentcenter.weave.bootstrap.common.security.resolver

import com.studentcenter.weave.application.user.service.util.UserTokenService
import com.studentcenter.weave.application.user.vo.UserTokenClaims
import com.studentcenter.weave.bootstrap.common.security.annotation.RegisterTokenClaim
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class RegisterTokenArgumentResolver(
    private val userTokenService: UserTokenService
) : HandlerMethodArgumentResolver {


    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(RegisterTokenClaim::class.java) != null
                && parameter.parameterType == UserTokenClaims.RegisterToken::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val request: HttpServletRequest = webRequest.nativeRequest as HttpServletRequest
        val registerToken: String = request.getHeader("Register-Token") ?: return null
        return userTokenService.resolveRegisterToken(registerToken)
    }

}
