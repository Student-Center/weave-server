package com.studentcenter.weave.infrastructure.redis.user.adapter

import com.studentcenter.weave.application.user.port.outbound.UserRefreshTokenRepository
import com.studentcenter.weave.infrastructure.redis.user.entity.UserRefreshTokenRedisHash
import com.studentcenter.weave.infrastructure.redis.user.repository.UserRefreshTokenRedisRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserRefreshTokenRedisAdapter(
    private val userRefreshTokenRedisRepository: UserRefreshTokenRedisRepository
) : UserRefreshTokenRepository {

    override fun save(
        userId: UUID,
        refreshToken: String,
        expirationSeconds: Long
    ) {
        val userRefreshTokenRedisHash = UserRefreshTokenRedisHash(
            id = userId,
            refreshToken = refreshToken,
            expirationSeconds = expirationSeconds,
        )
        userRefreshTokenRedisRepository.save(userRefreshTokenRedisHash)
    }

    override fun existsByUserId(userId: UUID): Boolean {
        return userRefreshTokenRedisRepository.existsById(userId)
    }

    override fun deleteByUserId(userId: UUID) {
        return userRefreshTokenRedisRepository.deleteById(userId)
    }

}
