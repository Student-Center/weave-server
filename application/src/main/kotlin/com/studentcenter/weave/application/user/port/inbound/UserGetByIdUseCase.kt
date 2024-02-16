package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.entity.User
import java.util.*

fun interface UserGetByIdUseCase {

    fun invoke(id: UUID): User

}
