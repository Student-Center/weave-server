package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.enums.SocialLoginProvider

fun interface SocialLogin {

    fun invoke(command: Command): Result

    data class Command(
        val socialLoginProvider: SocialLoginProvider,
        val idToken: String,
    )

    sealed class Result {

        data class Success(
            val accessToken: String,
            val refreshToken: String,
        ) : Result()

        data class NotRegistered(
            val registerToken: String,
        ) : Result()

    }

}
