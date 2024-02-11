package com.studentcenter.weave.infrastructure.persistence.user.adapter

import com.studentcenter.weave.application.user.port.outbound.UserUniversityVerificationInfoRepository
import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo
import com.studentcenter.weave.infrastructure.persistence.user.entity.UserUniveristyVerificationInfoJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.user.repository.UserUniversityVerificationInfoJpaRepository
import org.springframework.stereotype.Component

@Component
class UserUniversityVerificationInfoJpaAdapter(
    private val repository: UserUniversityVerificationInfoJpaRepository
) : UserUniversityVerificationInfoRepository {

    override fun save(domain: UserUniversityVerificationInfo) {
        repository.save(domain.toJpaEntity())
    }

}
