package com.studentcenter.weave.application.common.exception

import com.studentcenter.weave.support.common.exception.CustomExceptionType

enum class AuthExceptionType(override val code: String) : CustomExceptionType {
    REFRESH_TOKEN_NOT_FOUND("AUTH-001"),
    USER_NOT_AUTHENTICATED("AUTH-002"),
}
