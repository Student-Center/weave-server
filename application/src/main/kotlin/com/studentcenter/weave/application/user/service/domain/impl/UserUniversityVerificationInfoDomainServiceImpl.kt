package com.studentcenter.weave.application.user.service.domain.impl

import com.studentcenter.weave.application.user.port.outbound.UserUniversityVerificationInfoRepository
import com.studentcenter.weave.application.user.service.domain.UserUniversityVerificationInfoDomainService
import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo
import org.springframework.stereotype.Service

@Service
class UserUniversityVerificationInfoDomainServiceImpl(
    private val repository: UserUniversityVerificationInfoRepository,
) : UserUniversityVerificationInfoDomainService {

    override fun save(domain: UserUniversityVerificationInfo) {
        repository.save(domain)
    }

}
