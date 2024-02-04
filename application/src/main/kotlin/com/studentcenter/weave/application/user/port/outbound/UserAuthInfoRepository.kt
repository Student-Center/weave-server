package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.UserAuthInfo
import com.studentcenter.weave.support.common.vo.Email
import java.util.*

interface UserAuthInfoRepository {

    fun findByEmail(email: Email): UserAuthInfo?

    fun save(userAuthInfo: UserAuthInfo)

    fun getByUserId(userId: UUID): UserAuthInfo

    fun deleteById(id: UUID)

}
