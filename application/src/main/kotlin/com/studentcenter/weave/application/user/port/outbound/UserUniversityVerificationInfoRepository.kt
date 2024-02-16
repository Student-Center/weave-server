package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo
import com.studentcenter.weave.support.common.vo.Email
import java.util.*

interface UserUniversityVerificationInfoRepository {

    fun save(domain: UserUniversityVerificationInfo)

    fun existsByEmail(email: Email): Boolean

    fun existsByUserId(userId: UUID): Boolean

}
