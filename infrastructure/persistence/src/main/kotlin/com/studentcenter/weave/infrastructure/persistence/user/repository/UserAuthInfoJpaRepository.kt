package com.studentcenter.weave.infrastructure.persistence.user.repository

import com.studentcenter.weave.infrastructure.persistence.user.entity.UserAuthInfoJpaEntity
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserAuthInfoJpaRepository : JpaRepository<UserAuthInfoJpaEntity, UUID> {

    fun findByEmail(email: Email): UserAuthInfoJpaEntity?

    fun findByUserId(userId: UUID): UserAuthInfoJpaEntity?

}
