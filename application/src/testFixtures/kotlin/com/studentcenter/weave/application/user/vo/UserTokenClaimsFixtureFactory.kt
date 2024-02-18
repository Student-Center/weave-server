package com.studentcenter.weave.application.user.vo

import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory

object UserTokenClaimsFixtureFactory {

    fun createAccessTokenClaim(
        user: User = UserFixtureFactory.create()
    ): UserTokenClaims.AccessToken {
        return UserTokenClaims.AccessToken(
            userId = user.id,
            email = user.email,
            nickname = user.nickname,
            avatar = user.avatar,
            gender = user.gender,
        )
    }

}
