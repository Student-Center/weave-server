package com.studentcenter.weave.application.user.service.domain.impl

import com.studentcenter.weave.application.user.port.outbound.UserUniversityVerificationInfoRepository
import com.studentcenter.weave.application.user.service.domain.UserUniversityVerificationInfoDomainService
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Service

@Service
class UserUniversityVerificationInfoDomainServiceImpl(
    private val repository: UserUniversityVerificationInfoRepository,
) : UserUniversityVerificationInfoDomainService {

    override fun create(user: User, universityEmail: Email): UserUniversityVerificationInfo {
        return UserUniversityVerificationInfo.create(
            user = user,
            universityEmail = universityEmail,
        ).also { repository.save(it) }
    }

}
