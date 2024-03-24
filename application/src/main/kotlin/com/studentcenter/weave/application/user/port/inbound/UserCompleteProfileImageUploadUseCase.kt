package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.entity.UserProfileImage
import java.util.*

fun interface UserCompleteProfileImageUploadUseCase {

    fun invoke(command: Command)

    data class Command(
        val imageId: UUID,
        val extension: UserProfileImage.Extension,
    )

}
