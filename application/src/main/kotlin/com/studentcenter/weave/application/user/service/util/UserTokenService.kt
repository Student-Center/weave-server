package com.studentcenter.weave.application.user.service.util

import com.studentcenter.weave.application.user.vo.UserTokenClaims
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email

interface UserTokenService {

    fun resolveIdToken(
        idToken: String,
        provider: SocialLoginProvider,
    ): UserTokenClaims.IdToken

    fun generateRegisterToken(
        email: Email,
        nickname: Nickname,
        provider: SocialLoginProvider,
    ): String

    fun resolveRegisterToken(
        registerToken: String,
    ): UserTokenClaims.RegisterToken

    fun generateAccessToken(
        user: User,
    ): String

    fun resolveAccessToken(
        accessToken: String,
    ): UserTokenClaims.AccessToken

    fun generateRefreshToken(
        user: User,
    ): String

    fun resolveRefreshToken(
        refreshToken: String,
    ): UserTokenClaims.RefreshToken

}
