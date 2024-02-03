package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Email
import java.time.LocalDateTime
import java.util.UUID

data class DeletedUserInfo(
    val id: UUID = UuidCreator.create(),
    val email: Email,
    val socialLoginProvider: SocialLoginProvider,
    val reason: String? = null,
    val registeredAt: LocalDateTime,
    val deletedAt: LocalDateTime = LocalDateTime.now(),
) {

    init {
        require(reason?.isNotBlank()?.let { it && reason.length <= 100 } ?: true) {
            "최대 200자 이내로 탈퇴 사유를 입력해주세요."
        }
    }

    companion object {
        fun create(
            userAuthInfo: UserAuthInfo,
            reason: String? = null,
        ): DeletedUserInfo {
            return DeletedUserInfo(
                email = userAuthInfo.email,
                socialLoginProvider = userAuthInfo.socialLoginProvider,
                reason = reason,
                registeredAt = userAuthInfo.registeredAt,
            )
        }
    }

}
