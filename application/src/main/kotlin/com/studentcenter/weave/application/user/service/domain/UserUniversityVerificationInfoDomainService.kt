package com.studentcenter.weave.application.user.service.domain

import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo
import com.studentcenter.weave.support.common.vo.Email
import java.util.*

interface UserUniversityVerificationInfoDomainService {

    fun save(domain: UserUniversityVerificationInfo)

    fun existsByEmail(email: Email): Boolean

    fun existsByUserId(userId: UUID): Boolean

}
