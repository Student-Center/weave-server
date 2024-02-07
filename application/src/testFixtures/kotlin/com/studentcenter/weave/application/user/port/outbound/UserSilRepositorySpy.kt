package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.UserSil
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UserSilRepositorySpy : UserSilRepository {

    private val bucket = ConcurrentHashMap<UUID, UserSil>()

    override fun save(userSil: UserSil) {
        bucket[userSil.userId] = userSil
    }

    override fun getByUserId(userId: UUID): UserSil {
        return bucket.values.firstOrNull { it.userId == userId }
            ?: throw NoSuchElementException("UserSil not found by userId: $userId")
    }

    fun clear() {
        bucket.clear()
    }

}
