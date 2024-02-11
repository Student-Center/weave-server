package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UserUniversityVerificationInfoRepositorySpy : UserUniversityVerificationInfoRepository {

    private val bucket = ConcurrentHashMap<UUID, UserUniversityVerificationInfo>()

    override fun save(domain: UserUniversityVerificationInfo) {
        bucket[domain.userId] = domain
    }

    fun clear() {
        bucket.clear()
    }


}
