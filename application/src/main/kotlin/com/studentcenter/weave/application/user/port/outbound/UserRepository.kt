package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.User
import java.util.*

interface UserRepository {

    fun save(user: User)

    fun getById(id: UUID): User

    fun deleteById(id: UUID)

}
