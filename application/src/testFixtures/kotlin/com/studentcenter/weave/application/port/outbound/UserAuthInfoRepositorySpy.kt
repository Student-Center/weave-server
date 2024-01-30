package com.studentcenter.weave.application.port.outbound

import com.studentcenter.weave.domain.entity.UserAuthInfo
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

    fun clear() {
        bucket.clear()
    }

}
