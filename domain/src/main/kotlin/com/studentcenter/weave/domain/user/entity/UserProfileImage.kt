package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.domain.common.DomainEntity
import com.studentcenter.weave.domain.user.exception.UserException
import com.studentcenter.weave.support.common.vo.Url
import java.util.*

data class UserProfileImage(
    override val id: UUID,
    val extension: Extension,
    val imageUrl: Url,
) : DomainEntity {

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
            val imageUrl: Url = try {
                getProfileImageUrl(imageId, extension)
            } catch (e: Exception) {
                throw UserException.UserProfileImageUploadFailed()
            }

            return UserProfileImage(
                id = imageId,
                extension = extension,
                imageUrl = imageUrl,
            )
        }
    }

}
