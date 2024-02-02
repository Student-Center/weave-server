package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.User
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UserRepositorySpy : com.studentcenter.weave.application.user.port.outbound.UserRepository {

    private val bucket = ConcurrentHashMap<UUID, User>()

    fun findById(id: UUID): User? {
        return bucket[id]
    }

    override fun save(user: User) {
        bucket[user.id] = user
    }

    override fun getById(id: UUID): User {
        return bucket[id] ?: throw NoSuchElementException()
    }

    fun clear() {
        bucket.clear()
    }

}
