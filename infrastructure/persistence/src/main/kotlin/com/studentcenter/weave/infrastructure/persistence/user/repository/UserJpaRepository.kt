package com.studentcenter.weave.infrastructure.persistence.user.repository

import com.studentcenter.weave.infrastructure.persistence.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface UserJpaRepository : JpaRepository<UserJpaEntity, UUID> {

    fun findByKakaoId(kakaoId: String): Optional<UserJpaEntity>

}
