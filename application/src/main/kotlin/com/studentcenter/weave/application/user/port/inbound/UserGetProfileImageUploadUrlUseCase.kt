package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.entity.UserProfileImage
import com.studentcenter.weave.support.common.vo.Url
import java.util.*

fun interface UserGetProfileImageUploadUrlUseCase {

    fun invoke(extension: UserProfileImage.Extension): Result

    data class Result(
        val imageId: UUID,
        val extension: UserProfileImage.Extension,
        val uploadUrl: Url,
    )

}
