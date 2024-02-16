package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo
import com.studentcenter.weave.support.common.vo.Email
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UserUniversityVerificationInfoRepositorySpy : UserUniversityVerificationInfoRepository {

    private val bucket = ConcurrentHashMap<UUID, UserUniversityVerificationInfo>()

    override fun save(domain: UserUniversityVerificationInfo) {
        bucket[domain.id] = domain
    }

    override fun existsByEmail(email: Email): Boolean {
        return bucket.values.firstOrNull { it.universityEmail == email } != null
    }

    override fun existsByUserId(userId: UUID): Boolean {
        return bucket.values.firstOrNull { it.userId == userId } != null
    }

    fun clear() {
        bucket.clear()
    }


}
