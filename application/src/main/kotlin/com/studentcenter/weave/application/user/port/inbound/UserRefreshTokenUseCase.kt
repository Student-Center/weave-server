package com.studentcenter.weave.application.user.port.inbound


fun interface UserRefreshTokenUseCase {

    fun invoke(command: com.studentcenter.weave.application.user.port.inbound.UserRefreshTokenUseCase.Command): com.studentcenter.weave.application.user.port.inbound.UserRefreshTokenUseCase.Result

    data class Command(
        val refreshToken: String,
    )


    data class Result(
        val accessToken: String,
        val refreshToken: String,
    )

}
