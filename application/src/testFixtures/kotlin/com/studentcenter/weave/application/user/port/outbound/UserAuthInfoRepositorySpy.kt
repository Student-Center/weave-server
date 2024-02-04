package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.UserAuthInfo
import com.studentcenter.weave.support.common.vo.Email
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UserAuthInfoRepositorySpy : UserAuthInfoRepository {

    private val bucket = ConcurrentHashMap<UUID, UserAuthInfo>()

    override fun findByEmail(email: Email): UserAuthInfo? {
        return bucket.values.find { it.email == email }
    }

    override fun save(userAuthInfo: UserAuthInfo) {
        bucket[userAuthInfo.id] = userAuthInfo
    }

    override fun getByUserId(userId: UUID): UserAuthInfo {
        return bucket.values.find { it.userId == userId }!!
    }

    override fun deleteById(id: UUID) {
        bucket.remove(id)
    }

    fun clear() {
        bucket.clear()
    }

}
