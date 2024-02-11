package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo

interface UserUniversityVerificationInfoRepository {

    fun save(domain: UserUniversityVerificationInfo)

}
