package com.studentcenter.weave.bootstrap.user.dto

import com.studentcenter.weave.domain.user.entity.UserProfileImage
import java.util.UUID

data class UserCompleteProfileImageUploadRequest(
    val imageId: UUID,
    val extension: UserProfileImage.Extension,
)
