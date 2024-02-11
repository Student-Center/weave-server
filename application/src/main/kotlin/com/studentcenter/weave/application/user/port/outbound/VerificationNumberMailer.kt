package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.support.common.vo.Email
import kotlin.time.Duration

interface VerificationNumberMailer {

    fun send(to: Email, verificationNumber: String, expirationDuration: Duration)

}
