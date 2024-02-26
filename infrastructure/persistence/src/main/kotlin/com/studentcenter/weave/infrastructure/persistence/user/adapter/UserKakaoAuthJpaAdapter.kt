package com.studentcenter.weave.infrastructure.persistence.user.adapter

import com.studentcenter.weave.application.user.port.outbound.UserKakaoAuthRepository
import com.studentcenter.weave.domain.user.entity.UserKakaoAuth
import com.studentcenter.weave.infrastructure.persistence.user.entity.UserKakaoAuthJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.user.repository.UserKakaoAuthJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserKakaoAuthJpaAdapter(
    private val userKakaoAuthJpaRepository: UserKakaoAuthJpaRepository
) : UserKakaoAuthRepository {

    override fun findByUserId(userId: UUID): UserKakaoAuth? {
        return userKakaoAuthJpaRepository
            .findByUserId(userId)
            .orElse(null)
            .toDomain()
    }

    override fun findByKakaoId(kakaoId: String): UserKakaoAuth? {
        return userKakaoAuthJpaRepository
            .findByKakaoId(kakaoId)
            .orElse(null)
            .toDomain()
    }

    override fun save(userKakaoAuth: UserKakaoAuth) {
        userKakaoAuth
            .toJpaEntity()
            .also { userKakaoAuthJpaRepository.save(it) }
    }


}
