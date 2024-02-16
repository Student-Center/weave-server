package com.studentcenter.weave.infrastructure.persistence.user.adapter

import com.studentcenter.weave.application.user.port.outbound.UserUniversityVerificationInfoRepository
import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo
import com.studentcenter.weave.infrastructure.persistence.user.entity.UserUniveristyVerificationInfoJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.user.repository.UserUniversityVerificationInfoJpaRepository
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserUniversityVerificationInfoJpaAdapter(
    private val repository: UserUniversityVerificationInfoJpaRepository
) : UserUniversityVerificationInfoRepository {

    override fun save(domain: UserUniversityVerificationInfo) {
        repository.save(domain.toJpaEntity())
    }

    override fun existsByEmail(email: Email): Boolean {
        return repository.existsByUniversityEmail(email.value)
    }

    override fun existsByUserId(userId: UUID): Boolean {
        return repository.existsByUserId(userId)
    }

}
