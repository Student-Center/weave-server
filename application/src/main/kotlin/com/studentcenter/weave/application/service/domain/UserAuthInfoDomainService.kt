package com.studentcenter.weave.application.service.domain

import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserAuthInfo
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import com.studentcenter.weave.support.common.vo.Email

interface UserAuthInfoDomainService {

    fun findByEmail(email: Email): UserAuthInfo?

    fun create(
        user: User,
        socialLoginProvider: SocialLoginProvider,
    ): UserAuthInfo

}
