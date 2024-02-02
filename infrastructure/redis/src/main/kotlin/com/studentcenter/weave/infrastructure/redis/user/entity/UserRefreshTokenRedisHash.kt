package com.studentcenter.weave.infrastructure.redis.user.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.*

@RedisHash("user_refresh_token")
class UserRefreshTokenRedisHash(
    id: UUID,
    refreshToken: String,
    expirationSeconds: Long
) {

    @Id
    var id: UUID = id
        private set

    var refreshToken: String = refreshToken
        private set

    @TimeToLive
    var expirationSeconds: Long = expirationSeconds
        private set

}
