package com.studentcenter.weave.infrastructure.persistence.user.entity

import com.studentcenter.weave.domain.user.entity.UserAuthInfo
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import com.studentcenter.weave.support.common.vo.Email
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "user_auth_info")
class UserAuthInfoJpaEntity(
    id: UUID,
    userId: UUID,
    email: Email,
    socialLoginProvider: SocialLoginProvider,
    registeredAt: LocalDateTime,
) {

    @Id
    var id: UUID = id
        private set

    @Column(unique = true, nullable = false)
    var userId: UUID = userId
        private set

    @Column(unique = true, nullable = false)
    var email: Email = email
        private set

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(value = EnumType.STRING)
    var socialLoginProvider: SocialLoginProvider = socialLoginProvider
        private set

    @Column(nullable = false)
    var registeredAt: LocalDateTime = registeredAt
        private set

    companion object {

        fun UserAuthInfo.toJpaEntity(): UserAuthInfoJpaEntity {
            return UserAuthInfoJpaEntity(
                id = id,
                userId = userId,
                email = email,
                socialLoginProvider = socialLoginProvider,
                registeredAt = registeredAt,
            )
        }

    }

    fun toDomain(): UserAuthInfo {
        return UserAuthInfo(
            id = id,
            userId = userId,
            email = email,
            socialLoginProvider = socialLoginProvider,
            registeredAt = registeredAt,
        )
    }
}
