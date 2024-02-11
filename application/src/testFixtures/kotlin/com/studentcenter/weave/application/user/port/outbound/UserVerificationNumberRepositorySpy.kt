package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.application.user.vo.UserUniversityVerificationNumber
import com.studentcenter.weave.support.common.vo.Email
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UserVerificationNumberRepositorySpy : UserVerificationNumberRepository {

    private val bucket = ConcurrentHashMap<UUID, Pair<Email, UserUniversityVerificationNumber>>()

    override fun save(
        userId: UUID,
        universityEmail: Email,
        verificationNumber: UserUniversityVerificationNumber,
        expirationSeconds: Long
    ) {
        bucket[userId] = universityEmail to verificationNumber
    }

    override fun findByUserId(userId: UUID): Pair<Email, UserUniversityVerificationNumber>? {
        return bucket[userId]
    }

    override fun deleteByUserId(userId: UUID) {
        bucket.remove(userId)
    }

    fun clear() {
        bucket.clear()
    }

}
