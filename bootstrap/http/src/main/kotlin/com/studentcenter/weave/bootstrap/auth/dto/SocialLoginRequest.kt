package com.studentcenter.weave.bootstrap.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Social Login Request",
    description = "소셜 로그인 요청"
)
data class SocialLoginRequest(
    @Schema(description = "OpenId Connect Id Token")
    val idToken: String,
)
