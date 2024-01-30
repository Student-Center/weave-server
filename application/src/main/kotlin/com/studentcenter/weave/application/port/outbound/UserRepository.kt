package com.studentcenter.weave.application.port.outbound

import com.studentcenter.weave.domain.entity.User
import java.util.*

interface UserRepository {

    fun save(user: User)

    fun getById(id: UUID): User

}
