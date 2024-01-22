package com.studentcenter.weave.bootstrap.adapter.dto

import io.swagger.v3.oas.annotations.media.Schema


@Schema(
    name = "소셜 로그인 응답",
    oneOf = [
        SocialLoginResponse.Success::class,
        SocialLoginResponse.UserNotRegistered::class,
    ],
)
sealed class SocialLoginResponse {

    @Schema(
        name = "소셜 로그인 성공 응답",
        description = "소셜 로그인 성공시 access token, refresh token 을 반환합니다",
    )
    data class Success(
        val accessToken: String,
        val refreshToken: String,
    ) : SocialLoginResponse()

    @Schema(
        name = "소셜 로그인 실패 응답 - 회원 가입 필요",
        description = """
            회원 가입이 필요한 경우, 회원가입을 위한 사용자 정보가 담긴 register token을 반환합니다.
            추후 회원가입 API 호출시 해당 토큰을 header에 담아 전송해야 합니다.
        """,
    )
    data class UserNotRegistered(
        val registerToken: String,
    ) : SocialLoginResponse()

}
