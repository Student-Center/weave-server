package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.UserGetProfileImageUploadUrlUseCase
import com.studentcenter.weave.application.user.port.outbound.UserProfileImageUrlPort
import com.studentcenter.weave.application.user.vo.ImageFileExtension
import com.studentcenter.weave.support.common.vo.Url
import org.springframework.stereotype.Service

@Service
class UserGetProfileImageUploadUrlApplicationService(
    private val userProfileImageUrlPort: UserProfileImageUrlPort,
) : UserGetProfileImageUploadUrlUseCase {

    override fun invoke(imageFileExtension: ImageFileExtension): Url {
        return getCurrentUserAuthentication()
            .userId
            .let { userProfileImageUrlPort.getUploadUrlByUserId(it, imageFileExtension) }
    }

}
