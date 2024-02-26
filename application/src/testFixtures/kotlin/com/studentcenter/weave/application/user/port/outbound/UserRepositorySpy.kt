package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.vo.KakaoId
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

    override fun findByKakaoId(kakaoId: KakaoId): User? {
        return bucket.values.find { it.kakaoId == kakaoId }
    }

    override fun deleteById(id: UUID) {
        bucket.remove(id)
    }

    fun clear() {
        bucket.clear()
    }

}
