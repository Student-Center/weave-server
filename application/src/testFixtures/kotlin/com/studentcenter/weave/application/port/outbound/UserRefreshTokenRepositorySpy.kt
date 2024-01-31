package com.studentcenter.weave.application.port.outbound

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UserRefreshTokenRepositorySpy: UserRefreshTokenRepository {

    private val bucket = ConcurrentHashMap<UUID, String>()

    override fun save(
        id: UUID,
        refreshToken: String,
        expirationSeconds: Long
    ) {
        bucket[id] = refreshToken
    }

    override fun findByUserId(userId: UUID): String? {
        return bucket[userId]
    }

}
