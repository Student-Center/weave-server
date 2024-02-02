package com.studentcenter.weave.application.user.port.outbound

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UserRefreshTokenRepositorySpy :
    com.studentcenter.weave.application.user.port.outbound.UserRefreshTokenRepository {

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
