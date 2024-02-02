package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.domain.user.enums.SocialLoginProvider

object UserAuthInfoFixtureFactory {

    fun create(
        user: User = UserFixtureFactory.create(),
        socialLoginProvider: SocialLoginProvider = SocialLoginProvider.KAKAO,
    ): UserAuthInfo {
        return UserAuthInfo(
            userId = user.id,
            email = user.email,
            socialLoginProvider = socialLoginProvider,
            registeredAt = user.registeredAt,
        )
    }
}
