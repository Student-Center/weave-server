package com.studentcenter.weave.application.user.service.domain

import com.studentcenter.weave.domain.user.entity.UserAuthInfo

interface DeletedUserInfoService {

    fun create(
        userAuthInfo: UserAuthInfo,
        reason: String? = null,
    )

}
