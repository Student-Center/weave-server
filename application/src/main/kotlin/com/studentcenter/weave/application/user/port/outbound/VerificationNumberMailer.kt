package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.application.user.vo.UserUniversityVerificationNumber
import com.studentcenter.weave.support.common.vo.Email
import kotlin.time.Duration

fun interface VerificationNumberMailer {

    fun send(
        to: Email,
        verificationNumber: UserUniversityVerificationNumber,
        expirationDuration: Duration,
    )

}
