package com.studentcenter.weave.domain.entity

import com.studentcenter.weave.domain.enum.SocialLoginProvider
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Email
import java.time.LocalDateTime
import java.util.UUID

data class DeactivateUserInfo(
    val id: UUID = UuidCreator.create(),
    val email: Email,
    val socialLoginProvider: SocialLoginProvider,
    val reason: String? = null,
    val registeredAt: LocalDateTime,
    val deactivatedAt: LocalDateTime = LocalDateTime.now(),
) {

    companion object {
        fun create(
            user: User,
            userAuthInfo: UserAuthInfo,
            reason: String?,
        ): DeactivateUserInfo {
            return DeactivateUserInfo(
                email = user.email,
                socialLoginProvider = userAuthInfo.socialLoginProvider,
                reason = reason,
                registeredAt = userAuthInfo.registeredAt,
            )
        }
    }

}
