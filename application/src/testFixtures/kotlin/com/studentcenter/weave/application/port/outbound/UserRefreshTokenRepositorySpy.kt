package com.studentcenter.weave.application.port.outbound

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UserRefreshTokenRepositorySpy : UserRefreshTokenRepository {

    private val bucket = ConcurrentHashMap<UUID, String>()

    override fun save(
        userId: UUID,
        refreshToken: String,
        expirationSeconds: Long
    ) {
        bucket[userId] = refreshToken
    }

    override fun findByUserId(userId: UUID): String? {
        return bucket[userId]
    }

    override fun deleteByUserId(userId: UUID) {
        bucket.remove(userId)
    }

    fun clear() {
        bucket.clear()
    }

}