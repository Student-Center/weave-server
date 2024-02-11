package com.studentcenter.weave.application.user.service.domain

import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo

interface UserUniversityVerificationInfoDomainService {

    fun save(domain: UserUniversityVerificationInfo)

}
