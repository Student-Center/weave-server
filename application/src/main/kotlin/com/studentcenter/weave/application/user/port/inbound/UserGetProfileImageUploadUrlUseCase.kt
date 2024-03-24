package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.application.user.vo.ImageFileExtension
import com.studentcenter.weave.support.common.vo.Url
import java.util.UUID

fun interface UserGetProfileImageUploadUrlUseCase {

    fun invoke(imageFileExtension: ImageFileExtension): Result

    data class Result(
        val imageId: UUID,
        val imageFileExtension: ImageFileExtension,
        val uploadUrl: Url,
    )

}
