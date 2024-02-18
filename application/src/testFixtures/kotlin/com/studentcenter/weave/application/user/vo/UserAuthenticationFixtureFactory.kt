package com.studentcenter.weave.application.user.vo

import com.studentcenter.weave.domain.user.entity.User

object UserAuthenticationFixtureFactory {

    fun create(user: User) : UserAuthentication {
        return UserAuthentication(
            userId = user.id,
            nickname = user.nickname,
            email = user.email,
            avatar = user.avatar,
            gender = user.gender,
        )
    }

}
