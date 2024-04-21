package com.studentcenter.weave.application.common.exception

import com.studentcenter.weave.support.common.exception.CustomException


sealed class UniversityVerificationException(
    codeNumber: Int,
    message: String,
) : CustomException(CODE_PREFIX, codeNumber, message) {

    class VerificationInformationNotFound(message: String = "유저의 인증 요청을 찾을 수 없습니다.") :
        UniversityVerificationException(codeNumber = 1, message = message)

    class InvalidVerificationInformation(message: String = "인증 정보가 일치하지 않습니다.") :
        UniversityVerificationException(codeNumber = 2, message = message)

    class AlreadyVerifiedEmail(message: String = "이미 인증된 이메일입니다.") :
        UniversityVerificationException(codeNumber = 3, message = message)

    class AlreadyVerifiedUser(message: String = "이미 인증된 유저입니다.") :
        UniversityVerificationException(codeNumber = 4, message = message)

    companion object {
        const val CODE_PREFIX = "UNIV_VERIFICATION"
    }

}
