package com.studentcenter.weave.application.vo

import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.domain.entity.UserFixtureFactory

object UserTokenClaimsFixtureFactory {

    fun createAccessTokenClaim(
        user: User = UserFixtureFactory.create()
    ): UserTokenClaims.AccessToken {
        return UserTokenClaims.AccessToken(
            userId = user.id,
            email = user.email,
            nickname = user.nickname,
            avatar = user.avatar
        )
    }

}
