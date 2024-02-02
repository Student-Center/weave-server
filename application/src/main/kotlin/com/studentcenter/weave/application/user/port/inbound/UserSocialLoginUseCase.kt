package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.enums.SocialLoginProvider

fun interface UserSocialLoginUseCase {

    fun invoke(command: com.studentcenter.weave.application.user.port.inbound.UserSocialLoginUseCase.Command): com.studentcenter.weave.application.user.port.inbound.UserSocialLoginUseCase.Result

    data class Command(
        val socialLoginProvider: SocialLoginProvider,
        val idToken: String,
    )

    sealed class Result {

        data class Success(
            val accessToken: String,
            val refreshToken: String,
        ) : com.studentcenter.weave.application.user.port.inbound.UserSocialLoginUseCase.Result()

        data class NotRegistered(
            val registerToken: String,
        ) : com.studentcenter.weave.application.user.port.inbound.UserSocialLoginUseCase.Result()

    }

}
