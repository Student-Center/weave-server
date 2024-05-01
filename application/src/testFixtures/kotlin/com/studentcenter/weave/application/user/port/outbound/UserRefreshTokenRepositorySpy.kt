package com.studentcenter.weave.application.user.port.outbound

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

    override fun existsByUserId(userId: UUID): Boolean {
        return bucket[userId] != null
    }

    override fun deleteByUserId(userId: UUID) {
        bucket.remove(userId)
    }

    fun clear() {
        bucket.clear()
    }

}
