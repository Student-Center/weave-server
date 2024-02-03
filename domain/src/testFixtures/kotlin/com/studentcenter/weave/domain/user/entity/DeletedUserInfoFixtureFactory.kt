package com.studentcenter.weave.domain.user.entity

object DeletedUserInfoFixtureFactory {

    fun create(
        user: User = UserFixtureFactory.create(),
        userAuthInfo: UserAuthInfo = UserAuthInfoFixtureFactory.create(),
        reason: String? = null,
    ): DeletedUserInfo {
        return DeletedUserInfo(
            email = user.email,
            socialLoginProvider = userAuthInfo.socialLoginProvider,
            reason = reason,
            registeredAt = userAuthInfo.registeredAt,
        )
    }

}
