package com.studentcenter.weave.bootstrap.adapter.dto

import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.university.vo.MajorName
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.vo.Url
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "유저 마이페이지 응답")
data class UserGetMyProfileResponse(
    @Schema(description = "유저 아이디")
    val id: UUID,
    @Schema(description = "닉네임")
    val nickname: Nickname,
    @Schema(description = "생년")
    val birthYear: BirthYear,
    @Schema(description = "전공명")
    val majorName: MajorName,
    @Schema(description = "프로필 이미지")
    val avatar: Url?,
    @Schema(description = "MBTI")
    val mbti: Mbti,
    @Schema(description = "닮은 동물")
    val animalType: AnimalType?,
    @Schema(description = "키")
    val height: Int?,
    @Schema(description = "대학교 이메일 인증 여부")
    val isUniversityEmailVerified: Boolean,
)
