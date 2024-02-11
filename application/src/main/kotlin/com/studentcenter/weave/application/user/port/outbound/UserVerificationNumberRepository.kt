package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.support.common.vo.Email
import java.util.*

interface UserVerificationNumberRepository {

    fun save(
        userId: UUID,
        universityEmail: Email,
        verificationNumber: String,
        expirationSeconds: Long
    )

    fun findByUserId(userId: UUID): Pair<Email, String>?

    fun deleteByUserId(userId: UUID)

}
