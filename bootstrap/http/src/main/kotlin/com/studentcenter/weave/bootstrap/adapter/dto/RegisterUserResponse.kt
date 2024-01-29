package com.studentcenter.weave.bootstrap.adapter.dto

import com.studentcenter.weave.support.common.vo.Email
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Register User Response",
    oneOf = [
        RegisterUserResponse.Success::class,
        RegisterUserResponse.DuplicatedUserAuthInfo::class
    ]
)
sealed class RegisterUserResponse {

    @Schema(
        name = "Register User Response - Success",
        description = "회원가입 성공시 access token, refresh token 을 반환합니다",
    )
    data class Success(
        val accessToken: String,
        val refreshToken: String,
    ) : RegisterUserResponse()

    @Schema(
        name = "Register User Response - Duplicated User Auth Info",
        description = "이미 가입된 사용자 정보가 존재하는 경우, 해당 사용자의 이메일 정보를 반환합니다",
    )
    data class DuplicatedUserAuthInfo(
        val email: Email,
    ) : RegisterUserResponse()

}
