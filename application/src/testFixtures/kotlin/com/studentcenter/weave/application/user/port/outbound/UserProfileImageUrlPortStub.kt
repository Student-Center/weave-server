package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.UserProfileImage
import com.studentcenter.weave.support.common.vo.Url
import java.util.*

open class UserProfileImageUrlPortStub : UserProfileImageUrlPort {

    override fun getUploadImageUrl(
        imageId: UUID,
        extension: UserProfileImage.Extension
    ): Url {
        TODO("Not yet implemented")
    }

    override fun findByIdAndExtension(
        imageId: UUID,
        extension: UserProfileImage.Extension
    ): Url {
        TODO("Not yet implemented")
    }

}
