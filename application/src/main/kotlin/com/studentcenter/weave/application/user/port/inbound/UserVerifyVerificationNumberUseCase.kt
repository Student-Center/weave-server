package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.application.user.vo.UserUniversityVerificationNumber
import com.studentcenter.weave.support.common.vo.Email

fun interface UserVerifyVerificationNumberUseCase {

    fun invoke(command: Command)

    data class Command(
        val universityEmail: Email,
        val verificationNumber: UserUniversityVerificationNumber,
    )

}
