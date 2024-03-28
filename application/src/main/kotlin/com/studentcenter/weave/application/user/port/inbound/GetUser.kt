package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.entity.User
import java.util.*

interface GetUser {

    fun getById(id: UUID): User

}
