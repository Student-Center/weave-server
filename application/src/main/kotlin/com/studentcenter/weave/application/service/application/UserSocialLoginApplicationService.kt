package com.studentcenter.weave.application.service.application

import com.studentcenter.weave.application.port.inbound.UserSocialLoginUseCase
import com.studentcenter.weave.application.port.outbound.UserTokenHandler
import com.studentcenter.weave.application.service.domain.UserAuthInfoDomainService
import com.studentcenter.weave.application.service.domain.UserDomainService
import com.studentcenter.weave.application.vo.UserTokenClaims
import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.domain.entity.UserAuthInfo
import org.springframework.stereotype.Service

@Service
class UserSocialLoginApplicationService(
    private val userTokenHandler: UserTokenHandler,
    private val userDomainService: UserDomainService,
    private val userAuthInfoDomainService: UserAuthInfoDomainService,
) : UserSocialLoginUseCase {

    override fun invoke(command: UserSocialLoginUseCase.Command): UserSocialLoginUseCase.Result {
        val idTokenClaims: UserTokenClaims.IdToken = userTokenHandler.resolveIdToken(
            idToken = command.idToken,
            provider = command.socialLoginProvider,
        )

        val userAuthInfo: UserAuthInfo = userAuthInfoDomainService.findByEmail(idTokenClaims.email)
            ?: return UserSocialLoginUseCase.Result.NotRegistered(
                registerToken = userTokenHandler.generateRegisterToken(
                    email = idTokenClaims.email,
                    nickname = idTokenClaims.nickname,
                    provider = command.socialLoginProvider,
                ),
            )

        val user: User = userDomainService.getById(userAuthInfo.userId)
        return UserSocialLoginUseCase.Result.Success(
            accessToken = userTokenHandler.generateAccessToken(user),
            refreshToken = userTokenHandler.generateRefreshToken(user)
        )
    }

}
