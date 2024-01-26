package com.studentcenter.weave.application.service

import com.studentcenter.weave.application.port.outbound.UserTokenHandler
import com.studentcenter.weave.application.vo.UserTokenClaims
import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.domain.entity.UserFixtureFactory
import com.studentcenter.weave.domain.enum.SocialLoginProvider
import com.studentcenter.weave.support.common.vo.Email

class UserTokenHandlerStub : UserTokenHandler {

    private val user = UserFixtureFactory.create()

    override fun resolveIdToken(
        idToken: String,
        provider: SocialLoginProvider
    ): UserTokenClaims.IdToken {
        return UserTokenClaims.IdToken(
            nickname = user.nickname,
            email = user.email,
        )
    }

    override fun generateRegisterToken(
        email: Email,
        provider: SocialLoginProvider
    ): String {
        return "registerToken"
    }

    override fun resolveRegisterToken(registerToken: String): UserTokenClaims.RegisterToken {
        return UserTokenClaims.RegisterToken(
            nickname = user.nickname,
            email = user.email,
            socialLoginProvider = SocialLoginProvider.KAKAO,
        )
    }

    override fun generateAccessToken(user: User): String {
        return "accessToken"
    }

    override fun resolveAccessToken(accessToken: String): UserTokenClaims.AccessToken {
        return UserTokenClaims.AccessToken(
            userId = user.id,
            nickname = user.nickname,
            email = user.email,
            avatar = user.avatar,
        )
    }

    override fun generateRefreshToken(user: User): String {
        return "refreshToken"
    }

    override fun resolveRefreshToken(refreshToken: String): UserTokenClaims.RefreshToken {
        return UserTokenClaims.RefreshToken(
            userId = user.id,
        )
    }

}
