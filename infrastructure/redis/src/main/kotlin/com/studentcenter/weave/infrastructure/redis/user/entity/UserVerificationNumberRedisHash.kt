package com.studentcenter.weave.infrastructure.redis.user.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.*

@RedisHash("user_refresh_token")
class UserVerificationNumberRedisHash(
    id: UUID,
    verifiedEmail: String,
    verificationNumber: String,
    expirationSeconds: Long
) {

    @Id
    var id: UUID = id
        private set

    var verifiedEmail: String = verifiedEmail
        private set

    var verificationNumber: String = verificationNumber
        private set

    @TimeToLive
    var expirationSeconds: Long = expirationSeconds
        private set

}
