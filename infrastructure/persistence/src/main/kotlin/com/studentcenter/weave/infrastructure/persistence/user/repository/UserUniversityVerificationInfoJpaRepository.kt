package com.studentcenter.weave.infrastructure.persistence.user.repository

import com.studentcenter.weave.infrastructure.persistence.user.entity.UserUniveristyVerificationInfoJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserUniversityVerificationInfoJpaRepository : JpaRepository<UserUniveristyVerificationInfoJpaEntity, UUID> {

    fun existsByUniversityEmail(email: String): Boolean

    fun existsByUserId(userId: UUID): Boolean

}
