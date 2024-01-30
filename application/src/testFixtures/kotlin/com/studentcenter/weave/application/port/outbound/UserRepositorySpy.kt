package com.studentcenter.weave.application.port.outbound

import com.studentcenter.weave.domain.entity.User
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UserRepositorySpy : UserRepository {

    private val bucket = ConcurrentHashMap<UUID, User>()

    override fun save(user: User) {
        bucket[user.id] = user
    }

}
