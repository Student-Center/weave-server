package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.exception.AuthExceptionType
import com.studentcenter.weave.application.user.port.inbound.Logout
import com.studentcenter.weave.application.user.port.outbound.UserRefreshTokenRepository
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class LogoutApplicationService(
    private val userRefreshTokenRepository: UserRefreshTokenRepository,
) : Logout {

    override fun invoke() {
        val userAuthentication: UserAuthentication = SecurityContextHolder
            .getContext<UserAuthentication>()
            ?.getAuthentication() ?: throw CustomException(
            AuthExceptionType.USER_NOT_AUTHENTICATED,
            "회원이 인증되지 않았습니다."
        )

        userRefreshTokenRepository.deleteByUserId(userAuthentication.userId)
    }

}
