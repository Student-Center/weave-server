package com.studentcenter.weave.application.user.service.domain.impl

import com.studentcenter.weave.application.user.port.outbound.UserUniversityVerificationInfoRepository
import com.studentcenter.weave.application.user.service.domain.UserUniversityVerificationInfoDomainService
import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserUniversityVerificationInfoDomainServiceImpl(
    private val repository: UserUniversityVerificationInfoRepository,
) : UserUniversityVerificationInfoDomainService {

    override fun save(domain: UserUniversityVerificationInfo) {
        repository.save(domain)
    }

    override fun existsByEmail(email: Email): Boolean {
        return repository.existsByEmail(email)
    }

    override fun existsByUserId(userId: UUID): Boolean {
        return repository.existsByUserId(userId)
    }

}
