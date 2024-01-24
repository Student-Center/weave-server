package com.studentcenter.weave.bootstrap.adapter.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Register User Response", description = "회원 가입 응답")
data class RegisterUserResponse (
    val accessToken: String,
    val refreshToken: String,
)
