package com.studentcenter.weave.bootstrap.adapter.dto

import com.studentcenter.weave.domain.enum.SocialLoginProvider
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.Url
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
        description = "회원 가입이 필요한 경우, 회원가입을 위한 사용자 정보를 반환합니다",
    )
    data class UserNotRegistered(
        val name: String,
        val email: Email,
        val provider: SocialLoginProvider,
        val picture: Url?,
    ) : SocialLoginResponse()

}
