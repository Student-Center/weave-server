package com.studentcenter.weave.infrastructure.redis.user.adapter

import com.studentcenter.weave.application.user.port.outbound.UserVerificationNumberRepository
import com.studentcenter.weave.application.user.vo.UserUniversityVerificationNumber
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
        verificationNumber: UserUniversityVerificationNumber,
        expirationSeconds: Long,
    ) {
        val userVerificationNumberRedisHash = UserVerificationNumberRedisHash(
            id = userId,
            verifiedEmail = universityEmail.value,
            verificationNumber = verificationNumber.value,
            expirationSeconds = expirationSeconds,
        )
        userVerificationNumberRedisRepository.save(userVerificationNumberRedisHash)
    }

    override fun findByUserId(userId: UUID): Pair<Email, UserUniversityVerificationNumber>? {
        return userVerificationNumberRedisRepository
            .findById(userId)
            .map {
                Email(it.verifiedEmail) to UserUniversityVerificationNumber(it.verificationNumber)
            }
            .orElse(null)


    }

    override fun deleteByUserId(userId: UUID) {
        return userVerificationNumberRedisRepository.deleteById(userId)
    }


}
