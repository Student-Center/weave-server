package com.studentcenter.weave.application.service.application

import com.studentcenter.weave.application.port.inbound.UserSocialLoginUseCase
import com.studentcenter.weave.application.service.UserTokenService
import com.studentcenter.weave.application.service.domain.UserAuthInfoDomainService
import com.studentcenter.weave.application.service.domain.UserDomainService
import com.studentcenter.weave.application.vo.UserTokenClaims
import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.domain.entity.UserAuthInfo
import org.springframework.stereotype.Service

@Service
class UserSocialLoginApplicationService(
    private val userTokenService: UserTokenService,
    private val userDomainService: UserDomainService,
    private val userAuthInfoDomainService: UserAuthInfoDomainService,
) : UserSocialLoginUseCase {

    override fun invoke(command: UserSocialLoginUseCase.Command): UserSocialLoginUseCase.Result {
        val idTokenClaims: UserTokenClaims.IdToken = userTokenService.resolveIdToken(
            idToken = command.idToken,
            provider = command.socialLoginProvider,
        )

        val userAuthInfo: UserAuthInfo = userAuthInfoDomainService.findByEmail(idTokenClaims.email)
            ?: return UserSocialLoginUseCase.Result.NotRegistered(
                registerToken = userTokenService.generateRegisterToken(
                    email = idTokenClaims.email,
                    provider = command.socialLoginProvider,
                ),
            )

        val user: User = userDomainService.getById(userAuthInfo.userId)
        return UserSocialLoginUseCase.Result.Success(
            accessToken = userTokenService.generateAccessToken(user),
            refreshToken = userTokenService.generateRefreshToken(user)
        )
    }

}