package com.studentcenter.weave.infrastructure.persistence.user.repository

import com.studentcenter.weave.infrastructure.persistence.user.entity.UserKakaoAuthJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserKakaoAuthJpaRepository: JpaRepository<UserKakaoAuthJpaEntity, UUID> {

    fun findByUserId(userId: UUID): Optional<UserKakaoAuthJpaEntity>

    fun findByKakaoId(kakaoId: String): Optional<UserKakaoAuthJpaEntity>

}
