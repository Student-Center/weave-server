package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.user.port.inbound.UserGetProfileImageUploadUrlUseCase
import com.studentcenter.weave.application.user.port.outbound.UserProfileImageUrlPort
import com.studentcenter.weave.application.user.vo.ImageFileExtension
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Url
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserGetProfileImageUploadUrlApplicationService(
    private val userProfileImageUrlPort: UserProfileImageUrlPort,
) : UserGetProfileImageUploadUrlUseCase {

    override fun invoke(imageFileExtension: ImageFileExtension): UserGetProfileImageUploadUrlUseCase.Result {
        val imageId: UUID = UuidCreator.create()
        val uploadUrl: Url = userProfileImageUrlPort.getUploadImageUrl(
            imageId = imageId,
            imageFileExtension = imageFileExtension,
        )

        return UserGetProfileImageUploadUrlUseCase.Result(
            imageId = imageId,
            imageFileExtension = imageFileExtension,
            uploadUrl = uploadUrl,
        )
    }

}
