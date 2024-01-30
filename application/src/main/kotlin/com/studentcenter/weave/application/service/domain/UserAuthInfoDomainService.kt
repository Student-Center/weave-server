package com.studentcenter.weave.application.service.domain

import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.domain.entity.UserAuthInfo
import com.studentcenter.weave.domain.enum.SocialLoginProvider
import com.studentcenter.weave.support.common.vo.Email

interface UserAuthInfoDomainService {

    fun findByEmail(email: Email): UserAuthInfo?

    fun create(
        user: User,
        socialLoginProvider: SocialLoginProvider,
    ): UserAuthInfo

}
