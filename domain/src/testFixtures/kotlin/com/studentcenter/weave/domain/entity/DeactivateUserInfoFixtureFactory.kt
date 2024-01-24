package com.studentcenter.weave.domain.entity

object DeactivateUserInfoFixtureFactory {

    fun create(
        user: User = UserFixtureFactory.create(),
        userAuthInfo: UserAuthInfo = UserAuthInfoFixtureFactory.create(),
        reason: String? = null,
    ): DeactivateUserInfo {
        return DeactivateUserInfo(
            email = user.email,
            socialLoginProvider = userAuthInfo.socialLoginProvider,
            reason = reason,
            registeredAt = userAuthInfo.registeredAt,
        )
    }

}
