package com.studentcenter.weave.bootstrap.user.dto

import com.studentcenter.weave.application.user.port.inbound.UserGetProfileImageUploadUrlUseCase
import com.studentcenter.weave.domain.user.entity.UserProfileImage
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "유저 프로필 이미지 업로드 URL 응답")
data class UserGetProfileImageUploadUrlResponse(
    @Schema(description = "업로드 URL")
    val uploadUrl: String,
    @Schema(description = "이미지 ID")
    val imageId: UUID,
    @Schema(description = "이미지 확장자")
    val extension: UserProfileImage.Extension,
) {

    companion object {

        fun from(result: UserGetProfileImageUploadUrlUseCase.Result) =
            UserGetProfileImageUploadUrlResponse(
                uploadUrl = result.uploadUrl.value,
                imageId = result.imageId,
                extension = result.extension,
            )
    }
}
