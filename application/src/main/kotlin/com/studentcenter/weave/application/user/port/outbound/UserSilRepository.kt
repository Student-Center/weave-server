package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.UserSil
import java.util.UUID

interface UserSilRepository {

    fun save(userSil: UserSil)

    fun getByUserId(userId: UUID): UserSil

}
