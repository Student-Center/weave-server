package com.studentcenter.weave.application.user.service.domain

import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserAuthInfo
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import com.studentcenter.weave.support.common.vo.Email
import java.util.*

interface UserAuthInfoDomainService {

    fun findByEmail(email: Email): UserAuthInfo?

    fun getByUserId(userId: UUID): UserAuthInfo

    fun deleteById(id: UUID)

    fun create(
        user: User,
        socialLoginProvider: SocialLoginProvider,
    ): UserAuthInfo

}
