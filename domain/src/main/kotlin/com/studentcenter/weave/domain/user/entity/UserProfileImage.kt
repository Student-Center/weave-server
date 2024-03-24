package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.support.common.vo.Url
import java.util.*

data class UserProfileImage(
    val id: UUID,
    val extension: Extension,
    val imageUrl: Url,
) {

    enum class Extension(val value: String) {
        JPG("jpg"),
        JPEG("jpeg"),
        PNG("png"),
        GIF("gif"),
        SVG("svg"),
        WEBP("webp");
    }

    companion object {

        fun create(
            imageId: UUID,
            extension: Extension,
            getProfileImageUrl: (UUID, Extension) -> Url,
        ): UserProfileImage {
            return UserProfileImage(
                id = imageId,
                extension = extension,
                imageUrl = getProfileImageUrl(imageId, extension),
            )
        }
    }

}
