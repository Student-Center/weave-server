package com.studentcenter.weave.application.service.domain

import com.studentcenter.weave.domain.entity.UserAuthInfo
import com.studentcenter.weave.support.common.vo.Email

interface UserAuthInfoDomainService {

    fun findByEmail(email: Email): UserAuthInfo?

}
