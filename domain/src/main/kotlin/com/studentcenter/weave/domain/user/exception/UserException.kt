package com.studentcenter.weave.domain.user.exception

import com.studentcenter.weave.support.common.exception.CustomException

sealed class UserException(
    codeNumber: Int,
    message: String,
) : CustomException(CODE_PREFIX, codeNumber, message) {

    class UserProfileImageUploadFailed(message: String = "프로필 이미지가 정상적으로 업로드 되지 않았습니다. 다시시도해주세요") :
        UserException(codeNumber = 1, message = message)

    companion object {
        const val CODE_PREFIX = "USER"
    }

}
