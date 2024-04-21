package com.studentcenter.weave.application.common.exception

import com.studentcenter.weave.support.common.exception.CustomException

sealed class AuthException(
    codeNumber: Int,
    message: String,
) : CustomException(CODE_PREFIX, codeNumber, message) {

    class RefreshTokenNotFound(message: String = "") :
        AuthException(codeNumber = 1, message = message)

    class UserNotAuthenticated(message: String = "인증되지 않은 사용자 입니다") :
        AuthException(codeNumber = 2, message = message)

    companion object {
        const val CODE_PREFIX = "AUTH"
    }


}
