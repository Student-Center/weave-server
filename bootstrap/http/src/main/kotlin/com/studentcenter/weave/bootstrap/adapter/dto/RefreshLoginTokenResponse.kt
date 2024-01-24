package com.studentcenter.weave.bootstrap.adapter.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Refresh Login Token Response",
    description = "로그인 토큰 갱신 요청시 access token, refresh token 을 반환합니다"
)
data class RefreshLoginTokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
