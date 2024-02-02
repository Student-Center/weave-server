package com.studentcenter.weave.application.user.vo

import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.Url
import java.util.*

sealed class UserTokenClaims {

    data class IdToken(
        val nickname: Nickname,
        val email: Email,
    ) : UserTokenClaims()

    data class AccessToken(
        val userId: UUID,
        val nickname: Nickname,
        val email: Email,
        val avatar: Url?,
    ) : UserTokenClaims()

    data class RegisterToken(
        val nickname: Nickname,
        val email: Email,
        val socialLoginProvider: SocialLoginProvider,
    ) : UserTokenClaims()

    data class RefreshToken(
        val userId: UUID,
    ) : UserTokenClaims()

}
