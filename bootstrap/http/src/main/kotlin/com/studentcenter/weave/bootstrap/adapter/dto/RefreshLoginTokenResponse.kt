package com.studentcenter.weave.bootstrap.adapter.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "RefreshLoginTokenResponse",
    description = "로그인 토큰 갱신 응답",
)
data class RefreshLoginTokenResponse(
    @Schema(description = "access token")
    val accessToken: String,
    @Schema(description = "refresh token")
    val refreshToken: String,
)
