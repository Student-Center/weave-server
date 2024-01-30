package com.studentcenter.weave.application.port.outbound

import com.studentcenter.weave.domain.entity.User

interface UserRepository {

    fun save(user: User)

}
