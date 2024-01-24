package com.studentcenter.weave.bootstrap.adapter.dto

import com.studentcenter.weave.domain.enum.Gender
import com.studentcenter.weave.domain.enum.Mbti
import com.studentcenter.weave.domain.vo.BirthYear
import com.studentcenter.weave.domain.vo.Major
import com.studentcenter.weave.domain.vo.University
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Register User Request",
    description = "회원 가입 요청"
)
data class RegisterUserRequest(
    val gender: Gender,
    val birthYear: BirthYear,
    val mbti: Mbti,
    val university: University,
    val major: Major,
)
