package com.studentcenter.weave.domain.user.exception

import com.studentcenter.weave.support.common.exception.CustomExceptionType

enum class UserExceptionType(override val code: String) : CustomExceptionType {
    USER_PROFILE_IMAGE_UPLOAD_FAILED("USER-001"),
}
