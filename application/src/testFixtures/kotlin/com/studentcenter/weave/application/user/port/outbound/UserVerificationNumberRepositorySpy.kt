package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.support.common.vo.Email
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UserVerificationNumberRepositorySpy : UserVerificationNumberRepository {

    private val bucket = ConcurrentHashMap<UUID, Pair<Email, String>>()

    override fun save(
        userId: UUID,
        universityEmail: Email,
        verificationNumber: String,
        expirationSeconds: Long
    ) {
        bucket[userId] = universityEmail to verificationNumber
    }

    override fun findByUserId(userId: UUID): Pair<Email, String>? {
        return bucket[userId]
    }

    override fun deleteByUserId(userId: UUID) {
        bucket.remove(userId)
    }

    fun clear() {
        bucket.clear()
    }

}
