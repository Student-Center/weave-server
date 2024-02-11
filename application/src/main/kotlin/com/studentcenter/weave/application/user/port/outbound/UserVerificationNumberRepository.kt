package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.application.user.vo.UserUniversityVerificationNumber
import com.studentcenter.weave.support.common.vo.Email
import java.util.*

interface UserVerificationNumberRepository {

    fun save(
        userId: UUID,
        universityEmail: Email,
        verificationNumber: UserUniversityVerificationNumber,
        expirationSeconds: Long,
    )

    fun findByUserId(userId: UUID): Pair<Email, UserUniversityVerificationNumber>?

    fun deleteByUserId(userId: UUID)

}
