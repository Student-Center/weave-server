package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.application.user.vo.ImageFileExtension
import com.studentcenter.weave.support.common.vo.Url
import java.util.*

open class UserProfileImageUrlPortStub: UserProfileImageUrlPort {

    override fun getUploadImageUrl(
        imageId: UUID,
        imageFileExtension: ImageFileExtension
    ): Url {
        TODO("Not yet implemented")
    }

    override fun findAllByUserId(userId: UUID): List<Url> {
        TODO("Not yet implemented")
    }

    override fun deleteByUrl(url: Url) {
        TODO("Not yet implemented")
    }

}
