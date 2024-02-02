package com.studentcenter.weave.bootstrap.common.security.filter

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.common.security.vo.UserAuthentication
import com.studentcenter.weave.application.service.util.UserTokenService
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val userTokenService: UserTokenService,
) : OncePerRequestFilter() {

    companion object {

        const val TOKEN_HEADER = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        extractToken(request)?.let { processToken(it) }
        filterChain.doFilter(request, response)
        SecurityContextHolder.clearContext()
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val bearerToken: String? = request.getHeader(TOKEN_HEADER)
        return if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            bearerToken.substring(TOKEN_PREFIX.length)
        } else null
    }

    private fun processToken(token: String) {
        with(userTokenService.resolveAccessToken(token)) {
            UserAuthentication.from(this)
        }.let { userAuthentication ->
            UserSecurityContext(userAuthentication)
        }.also { securityContext ->
            SecurityContextHolder.setContext(securityContext)
        }
    }

}
