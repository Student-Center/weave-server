package com.studentcenter.weave.support.security.jwt.exception

import com.studentcenter.weave.support.common.exception.CustomException


sealed class JwtException(
    codeNumber: Int,
    message: String,
) : CustomException(CODE_PREFIX, codeNumber, message) {

    class DecodeException(message: String = "유효하지 않은 토큰입니다.") :
        JwtException(codeNumber = 1, message = message)

    class Expired(message: String = "토큰이 만료되었습니다.") :
        JwtException(codeNumber = 2, message = message)

    class VerificationException(message: String = "유효하지 않은 토큰입니다.") :
        JwtException(codeNumber = 3, message = message)

    companion object {
        const val CODE_PREFIX = "JWT"
    }

}
