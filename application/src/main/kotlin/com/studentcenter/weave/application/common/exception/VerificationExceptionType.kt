package com.studentcenter.weave.application.common.exception

import com.studentcenter.weave.support.common.exception.CustomExceptionType

enum class VerificationExceptionType (override val code: String): CustomExceptionType {
    VERIFICATION_NUMBER_NOT_FOUND("VERIFICATION-001"),
    INVALID_VERIFICATION_INFOMATION("VERIFICATION-002"),
}
