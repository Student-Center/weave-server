package com.studentcenter.weave.application.port.outbound

import com.studentcenter.weave.domain.user.entity.UserAuthInfo
import com.studentcenter.weave.support.common.vo.Email

interface UserAuthInfoRepository {

    fun findByEmail(email: Email): UserAuthInfo?

    fun save(userAuthInfo: UserAuthInfo)

}
