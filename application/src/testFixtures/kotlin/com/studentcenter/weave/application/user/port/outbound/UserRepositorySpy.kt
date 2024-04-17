package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.vo.KakaoId
import com.studentcenter.weave.support.common.vo.Email
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.HashSet

class UserRepositorySpy : UserRepository {

    private val bucket = ConcurrentHashMap<UUID, User>()
    private val preRegisteredBucket = HashSet<Email>()

    fun findById(id: UUID): User? {
        return bucket[id]
    }

    override fun save(user: User) {
        bucket[user.id] = user
    }

    override fun getById(id: UUID): User {
        return bucket[id] ?: throw NoSuchElementException()
    }

    override fun getAllByIds(ids: List<UUID>): List<User> {
        return bucket.filterKeys { ids.contains(it) }.values.toList()
    }

    override fun findByKakaoId(kakaoId: KakaoId): User? {
        return bucket.values.find { it.kakaoId == kakaoId }
    }

    override fun deleteById(id: UUID) {
        bucket.remove(id)
    }

    override fun countAll(): Int {
        return bucket.keys.size
    }

    override fun isPreRegisteredEmail(email: Email): Boolean {
        return preRegisteredBucket.contains(email)
    }

    fun addPreRegisterEmail(email: Email) {
        preRegisteredBucket.add(email)
    }

    fun clear() {
        bucket.clear()
    }

}
