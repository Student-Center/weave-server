package com.studentcenter.weave.application.user.service.domain

import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo
import com.studentcenter.weave.support.common.vo.Email

interface UserUniversityVerificationInfoDomainService {

    fun create(user: User, universityEmail: Email): UserUniversityVerificationInfo

}
