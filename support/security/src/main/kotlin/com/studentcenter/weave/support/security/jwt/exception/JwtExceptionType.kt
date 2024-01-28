package com.studentcenter.weave.support.security.jwt.exception

import com.studentcenter.weave.support.common.exception.CustomExceptionType

enum class JwtExceptionType(override val code: String) : CustomExceptionType {
    JWT_DECODE_EXCEPTION("JWT-001"),
    JWT_EXPIRED_EXCEPTION("JWT-002"),
    JWT_VERIFICATION_EXCEPTION("JWT-003"),
}
