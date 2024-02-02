package com.studentcenter.weave.bootstrap.user.dto

import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.domain.user.vo.BirthYear
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(
    name = "Register User Request",
    description = "회원 가입 요청"
)
data class UserRegisterRequest(
    val gender: Gender,
    val birthYear: BirthYear,
    val mbti: Mbti,
    val universityId: UUID,
    val majorId: UUID,
)
