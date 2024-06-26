package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.domain.common.DomainEntity
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Email
import java.time.LocalDateTime
import java.util.*

data class UserUniversityVerificationInfo(
    override val id: UUID = UuidCreator.create(),
    val userId: UUID,
    val universityId: UUID,
    val universityEmail: Email,
    val verifiedAt: LocalDateTime,
) : DomainEntity {

    companion object {
        fun create(user: User, universityEmail: Email): UserUniversityVerificationInfo {
            return UserUniversityVerificationInfo(
                userId = user.id,
                universityId = user.universityId,
                universityEmail = universityEmail,
                verifiedAt = LocalDateTime.now(),
            )
        }
    }

}
