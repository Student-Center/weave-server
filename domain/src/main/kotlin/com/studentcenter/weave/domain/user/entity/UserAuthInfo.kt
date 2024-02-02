package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Email
import java.time.LocalDateTime
import java.util.*

data class UserAuthInfo(
    val id: UUID = UuidCreator.create(),
    val userId: UUID,
    val email: Email,
    val socialLoginProvider: SocialLoginProvider,
    val registeredAt: LocalDateTime,
) {

    companion object {
        fun create(
            user: User,
            socialLoginProvider: SocialLoginProvider,
        ): UserAuthInfo {
            return UserAuthInfo(
                userId = user.id,
                email = user.email,
                socialLoginProvider = socialLoginProvider,
                registeredAt = LocalDateTime.now(),
            )
        }
    }
}
