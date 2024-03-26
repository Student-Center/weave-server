package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.domain.user.exception.UserExceptionType
import com.studentcenter.weave.support.common.exception.CustomException
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
            val imageUrl: Url = try {
                getProfileImageUrl(imageId, extension)
            } catch (e: Exception) {
                throw CustomException(
                    type = UserExceptionType.USER_PROFILE_IMAGE_UPLOAD_FAILED,
                    message = "프로필 이미지가 정상적으로 업로드 되지 않았습니다. 다시시도해주세요",
                )
            }

            return UserProfileImage(
                id = imageId,
                extension = extension,
                imageUrl = imageUrl,
            )
        }
    }

}
