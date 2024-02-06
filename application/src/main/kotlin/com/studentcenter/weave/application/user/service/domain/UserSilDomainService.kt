package com.studentcenter.weave.application.user.service.domain

import com.studentcenter.weave.domain.user.entity.UserSil
import java.util.UUID

interface UserSilDomainService {

    fun create(userId: UUID): UserSil

    fun incrementByUserId(userId: UUID, amount: Long): UserSil

}
