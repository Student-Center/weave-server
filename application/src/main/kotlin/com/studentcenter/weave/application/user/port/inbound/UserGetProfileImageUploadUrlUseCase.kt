package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.application.user.vo.ImageFileExtension
import com.studentcenter.weave.support.common.vo.Url

interface UserGetProfileImageUploadUrlUseCase {

    fun invoke(imageFileExtension: ImageFileExtension): Url

}
