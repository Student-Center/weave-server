package com.studentcenter.weave.infrastructure.persistence.repository

import com.studentcenter.weave.infrastructure.persistence.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserJpaRepository : JpaRepository<UserJpaEntity, UUID>
