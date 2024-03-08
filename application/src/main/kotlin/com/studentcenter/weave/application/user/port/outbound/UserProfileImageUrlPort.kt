package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.application.user.vo.ImageFileExtension
import com.studentcenter.weave.support.common.vo.Url
import java.util.*

interface UserProfileImageUrlPort {

    fun getUploadUrlByUserId(
        userId: UUID,
        imageFileExtension: ImageFileExtension,
    ): Url

    fun findAllByUserId(userId: UUID): List<Url>

    // TODO : Asynchronous
    fun deleteByUrl(url: Url)

}
