package com.studentcenter.weave.infrastructure.persistence.user.repository

import com.studentcenter.weave.infrastructure.persistence.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserJpaRepository : JpaRepository<UserJpaEntity, UUID> {

    fun findByKakaoId(kakaoId: String): UserJpaEntity?

    @Query("select u from UserJpaEntity u where u.id in :ids")
    fun findAllByIds(ids: List<UUID>): List<UserJpaEntity>

    override fun count(): Long

}
