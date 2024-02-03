package com.studentcenter.weave.bootstrap.user.dto

import com.studentcenter.weave.domain.user.enums.Gender
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(
    name = "Register User Request",
    description = "회원 가입 요청"
)
data class UserRegisterRequest(
    val gender: Gender,
    val birthYear: Int,
    val mbti: String,
    val universityId: UUID,
    val majorId: UUID,
)
