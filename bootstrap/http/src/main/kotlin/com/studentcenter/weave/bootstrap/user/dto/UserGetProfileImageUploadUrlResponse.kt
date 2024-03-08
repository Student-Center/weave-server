package com.studentcenter.weave.bootstrap.user.dto

import com.studentcenter.weave.support.common.vo.Url
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "유저 프로필 이미지 업로드 URL 응답")
data class UserGetProfileImageUploadUrlResponse(
    @Schema(description = "업로드 URL")
    val uploadUrl: String
) {
    companion object {
        fun from(url: Url) = UserGetProfileImageUploadUrlResponse(url.value)
    }
}
