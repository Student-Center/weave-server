package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.exception.AuthException
import com.studentcenter.weave.application.user.port.inbound.Logout
import com.studentcenter.weave.application.user.port.outbound.UserRefreshTokenRepository
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class LogoutService(
    private val userRefreshTokenRepository: UserRefreshTokenRepository,
) : Logout {

    override fun invoke() {
        val userAuthentication: UserAuthentication = SecurityContextHolder
            .getContext<UserAuthentication>()
            ?.getAuthentication() ?: throw AuthException.UserNotAuthenticated()

        userRefreshTokenRepository.deleteByUserId(userAuthentication.userId)
    }

}
