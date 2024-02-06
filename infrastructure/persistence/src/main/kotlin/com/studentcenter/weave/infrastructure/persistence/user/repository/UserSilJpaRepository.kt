package com.studentcenter.weave.infrastructure.persistence.user.repository

import com.studentcenter.weave.infrastructure.persistence.user.entity.UserSilJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserSilJpaRepository : JpaRepository<UserSilJpaEntity, UUID> {

    fun findByUserId(userId: UUID): UserSilJpaEntity?

}
