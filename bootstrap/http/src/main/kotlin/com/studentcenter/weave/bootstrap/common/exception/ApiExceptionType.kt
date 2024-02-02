package com.studentcenter.weave.bootstrap.common.exception

import com.studentcenter.weave.support.common.exception.CustomExceptionType

enum class ApiExceptionType(override val code: String) : CustomExceptionType {
    INVALID_DATE_EXCEPTION("API-001"),
    INVALID_PARAMETER("API-002"),
    UNAUTHORIZED_REQUEST("API-003"),
}
