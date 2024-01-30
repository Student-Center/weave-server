package com.studentcenter.weave.application.port.outbound

import com.studentcenter.weave.domain.entity.User
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UserRepositorySpy : UserRepository {

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
