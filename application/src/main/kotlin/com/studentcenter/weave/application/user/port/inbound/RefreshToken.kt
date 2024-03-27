package com.studentcenter.weave.application.user.port.inbound


fun interface RefreshToken {

    fun invoke(command: Command): Result

    data class Command(
        val refreshToken: String,
    )


    data class Result(
        val accessToken: String,
        val refreshToken: String,
    )

}
