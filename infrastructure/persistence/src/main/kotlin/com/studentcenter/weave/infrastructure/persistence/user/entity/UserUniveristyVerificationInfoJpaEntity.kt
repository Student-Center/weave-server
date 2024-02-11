package com.studentcenter.weave.infrastructure.persistence.user.entity

import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo
import com.studentcenter.weave.support.common.vo.Email
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "user_university_verification_info")
class UserUniveristyVerificationInfoJpaEntity(
    id: UUID,
    userId: UUID,
    universityId: UUID,
    universityEmail: String,
    verifiedAt: LocalDateTime,
) {

    @Id
    var id: UUID = id
        private set

    @Column(unique = true, nullable = false, updatable = false)
    var userId: UUID = userId
        private set

    @Column(nullable = false, updatable = false)
    var universityId: UUID = universityId
        private set

    @Column(unique = true, nullable = false, updatable = false)
    var universityEmail: String = universityEmail
        private set

    @Column(nullable = false, updatable = false)
    var verifiedAt: LocalDateTime = verifiedAt
        private set

    companion object {

        fun UserUniversityVerificationInfo.toJpaEntity(): UserUniveristyVerificationInfoJpaEntity {
            return UserUniveristyVerificationInfoJpaEntity(
                id = id,
                userId = userId,
                universityId = universityId,
                universityEmail = universityEmail.value,
                verifiedAt = verifiedAt,
            )
        }

    }

    fun toDomain(): UserUniversityVerificationInfo {
        return UserUniversityVerificationInfo(
            id = id,
            userId = userId,
            universityId = universityId,
            universityEmail = Email(universityEmail),
            verifiedAt = verifiedAt,
        )
    }
}
