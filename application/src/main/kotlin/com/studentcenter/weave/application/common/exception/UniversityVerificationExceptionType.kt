package com.studentcenter.weave.application.common.exception

import com.studentcenter.weave.support.common.exception.CustomExceptionType

enum class UniversityVerificationExceptionType (override val code: String): CustomExceptionType {
    VERIFICATION_INFORMATION_NOT_FOUND("VERIFICATION-001"),
    INVALID_VERIFICATION_INFORMATION("VERIFICATION-002"),
    VERIFICATED_EMAIL("VERIFICATION-003"),
    VERIFICATED_USER("VERIFICATION-004"),
}
