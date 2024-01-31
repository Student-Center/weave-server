package com.studentcenter.weave.infrastructure.redis.adapter

import com.studentcenter.weave.application.port.outbound.UserRefreshTokenRepository
import com.studentcenter.weave.infrastructure.redis.entity.UserRefreshTokenRedisHash
import com.studentcenter.weave.infrastructure.redis.repository.UserRefreshTokenRedisRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserRefreshTokenRedisAdapter(
    private val userRefreshTokenRedisRepository: UserRefreshTokenRedisRepository
) : UserRefreshTokenRepository {

    override fun save(
        id: UUID,
        refreshToken: String,
        expirationSeconds: Long
    ) {
        val userRefreshTokenRedisHash = UserRefreshTokenRedisHash(
            id = id,
            refreshToken = refreshToken,
            expirationSeconds = expirationSeconds,
        )
        userRefreshTokenRedisRepository.save(userRefreshTokenRedisHash)
    }

    override fun findByUserId(userId: UUID): String? {
        return userRefreshTokenRedisRepository
            .findById(userId)
            .map { it.refreshToken }
            .orElse(null)
    }


}
