package com.studentcenter.weave.application.user.vo

import java.time.Instant

@JvmInline
value class UserUniversityVerificationNumber(
    val value: String
) {
    init {
        require(value.length == VERIFICATION_NUMBER_SIZE) {
            "${value}: 학교 인증 번호는 ${VERIFICATION_NUMBER_SIZE}자리이어야 합니다."
        }
        require(value.all { it.isDigit() }) {
            "학교 인증 번호는 숫자로 구성되어야 합니다."
        }
    }

    companion object {
        const val VERIFICATION_NUMBER_SIZE = 6

        fun generate(): UserUniversityVerificationNumber {
            var verificationNumber = Instant.now().nano.toString()

            if (verificationNumber.length < VERIFICATION_NUMBER_SIZE) {
                verificationNumber = verificationNumber.padStart(VERIFICATION_NUMBER_SIZE, '0')
            }

            if (verificationNumber.length > VERIFICATION_NUMBER_SIZE) {
                verificationNumber = verificationNumber.substring(0, VERIFICATION_NUMBER_SIZE)
            }

            return UserUniversityVerificationNumber(verificationNumber)
        }
    }
}
