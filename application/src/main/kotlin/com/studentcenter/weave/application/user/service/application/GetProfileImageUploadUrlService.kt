package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.user.port.inbound.GetProfileImageUploadUrl
import com.studentcenter.weave.application.user.port.outbound.UserProfileImageUrlPort
import com.studentcenter.weave.domain.user.entity.UserProfileImage
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Url
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetProfileImageUploadUrlService(
    private val userProfileImageUrlPort: UserProfileImageUrlPort,
) : GetProfileImageUploadUrl {

    override fun invoke(extension: UserProfileImage.Extension): GetProfileImageUploadUrl.Result {
        val imageId: UUID = UuidCreator.create()
        val uploadUrl: Url = userProfileImageUrlPort.getUploadImageUrl(
            imageId = imageId,
            extension = extension,
        )

        return GetProfileImageUploadUrl.Result(
            imageId = imageId,
            extension = extension,
            uploadUrl = uploadUrl,
        )
    }

}
