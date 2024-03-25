package com.studentcenter.weave.bootstrap.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "유저 마이페이지 응답")
data class UserGetMyProfileResponse(
    @Schema(description = "유저 아이디")
    val id: UUID,
    @Schema(description = "닉네임")
    val nickname: String,
    @Schema(description = "생년")
    val birthYear: Int,
    @Schema(description = "대학명")
    val universityName: String,
    @Schema(description = "전공명")
    val majorName: String,
    @Schema(description = "프로필 이미지")
    val avatar: String?,
    @Schema(description = "MBTI")
    val mbti: String,
    @Schema(description = "닮은 동물")
    val animalType: String?,
    @Schema(description = "키")
    val height: Int?,
    @Schema(description = "카카오 아이디")
    val kakaoId: String?,
    @Schema(description = "대학교 이메일 인증 여부")
    val isUniversityEmailVerified: Boolean,
    @Schema(description = "실(포인트)")
    val sil: Long,
)
