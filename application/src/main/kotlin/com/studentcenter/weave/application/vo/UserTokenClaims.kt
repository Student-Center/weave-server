package com.studentcenter.weave.application.vo

import com.studentcenter.weave.domain.enum.SocialLoginProvider
import com.studentcenter.weave.domain.vo.Nickname
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
