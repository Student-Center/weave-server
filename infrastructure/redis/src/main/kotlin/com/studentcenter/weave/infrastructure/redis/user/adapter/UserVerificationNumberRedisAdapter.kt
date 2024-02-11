package com.studentcenter.weave.infrastructure.redis.user.adapter

import com.studentcenter.weave.application.user.port.outbound.UserVerificationNumberRepository
import com.studentcenter.weave.infrastructure.redis.user.entity.UserVerificationNumberRedisHash
import com.studentcenter.weave.infrastructure.redis.user.repository.UserVerificationNumberRedisRepository
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserVerificationNumberRedisAdapter(
    private val userVerificationNumberRedisRepository: UserVerificationNumberRedisRepository,
) : UserVerificationNumberRepository {
    override fun save(
        userId: UUID,
        universityEmail: Email,
        verificationNumber: String,
        expirationSeconds: Long
    ) {
        val userVerificationNumberRedisHash = UserVerificationNumberRedisHash(
            id = userId,
            verifiedEmail = universityEmail.value,
            verificationNumber = verificationNumber,
            expirationSeconds = expirationSeconds,
        )
        userVerificationNumberRedisRepository.save(userVerificationNumberRedisHash)
    }

    override fun findByUserId(userId: UUID): Pair<Email, String>? {
        return userVerificationNumberRedisRepository
            .findById(userId)
            .map { Email(it.verifiedEmail) to it.verificationNumber }
            .orElse(null)


    }

    override fun deleteByUserId(userId: UUID) {
        return userVerificationNumberRedisRepository.deleteById(userId)
    }


}
