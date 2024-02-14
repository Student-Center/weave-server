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
    @Schema(example = "018d7782-f105-7f0e-9ad9-a47e213037d0")
    val universityId: UUID,
    @Schema(example = "018d79c6-e9be-7149-806e-7c42a7cd6311")
    val majorId: UUID,
)
