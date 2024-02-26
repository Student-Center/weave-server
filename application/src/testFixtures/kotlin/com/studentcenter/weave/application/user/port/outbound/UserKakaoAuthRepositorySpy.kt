package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.UserKakaoAuth
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UserKakaoAuthRepositorySpy: UserKakaoAuthRepository {

    private val bucket = ConcurrentHashMap<UUID, UserKakaoAuth>()

    override fun findByUserId(userId: UUID): UserKakaoAuth? {
        println(bucket.values)
        return bucket.values.find { it.userId == userId }
    }

    override fun findByKakaoId(kakaoId: String): UserKakaoAuth? {
        return bucket.values.find { it.kakaoId == kakaoId }
    }

    override fun save(userKakaoAuth: UserKakaoAuth) {
        bucket[userKakaoAuth.id] = userKakaoAuth
    }

    fun clear() {
        bucket.clear()
    }
}
