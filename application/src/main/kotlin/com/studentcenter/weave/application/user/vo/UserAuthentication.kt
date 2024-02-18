package com.studentcenter.weave.application.user.vo

import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.Url
import com.studentcenter.weave.support.security.authority.Authentication
import java.util.*

data class UserAuthentication(
    val userId: UUID,
    val nickname: Nickname,
    val email: Email,
    val avatar: Url?,
    val gender: Gender,
) : Authentication {

    companion object {
        fun from(claims: UserTokenClaims.AccessToken): UserAuthentication {
            return UserAuthentication(
                userId = claims.userId,
                nickname = claims.nickname,
                email = claims.email,
                avatar = claims.avatar,
                gender = claims.gender
            )
        }
    }

}
