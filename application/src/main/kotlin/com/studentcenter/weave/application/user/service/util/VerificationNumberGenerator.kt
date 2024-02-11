package com.studentcenter.weave.application.user.service.util

import java.time.Instant

object VerificationNumberGenerator {
    fun generate(size: Int): String {
        if (size <= 0) {
            throw IllegalArgumentException()
        }

        val verificationNumber = Instant.now().nano.toString()
        if (verificationNumber.length == size) {
            return verificationNumber
        }

        if (verificationNumber.length < size) {
            return verificationNumber.padStart(size, '0')
        }

        return verificationNumber.substring(0, size)
    }
}
