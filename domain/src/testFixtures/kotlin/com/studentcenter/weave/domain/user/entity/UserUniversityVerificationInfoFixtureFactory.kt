package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Email
import java.time.LocalDateTime
import java.util.UUID

object UserUniversityVerificationInfoFixtureFactory {

    fun create(
        id: UUID = UuidCreator.create(),
        user: User = UserFixtureFactory.create(),
        universityEmail: Email = Email("${UUID.randomUUID()}@weave.com"),
        verifiedAt: LocalDateTime = LocalDateTime.now().minusMinutes(1)
    ): UserUniversityVerificationInfo {
        return UserUniversityVerificationInfo(
            id = id,
            userId = user.id,
            universityId = user.universityId,
            universityEmail = universityEmail,
            verifiedAt = verifiedAt,
        )
    }
}
