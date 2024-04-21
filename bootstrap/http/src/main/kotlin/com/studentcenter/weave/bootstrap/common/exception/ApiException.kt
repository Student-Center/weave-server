package com.studentcenter.weave.bootstrap.common.exception

import com.studentcenter.weave.support.common.exception.CustomException

sealed class ApiException(
    codeNumber: Int,
    message: String,
) : CustomException(CODE_PREFIX, codeNumber, message) {

    class InvalidDateException(message: String = "") :
        ApiException(codeNumber = 1, message = message)

    class InvalidParameter(message: String = "") :
        ApiException(codeNumber = 2, message = message)

    class UnauthorizedRequest(message: String = "") :
        ApiException(codeNumber = 3, message = message)

    companion object {
        const val CODE_PREFIX = "API"
    }

}
