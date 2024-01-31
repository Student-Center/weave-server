package com.studentcenter.weave.application.port.outbound

import java.util.*

interface UserRefreshTokenRepository {

    fun save(
        userId: UUID,
        refreshToken: String,
        expirationSeconds: Long
    )

    fun findByUserId(userId: UUID): String?

    fun deleteByUserId(userId: UUID)

}
