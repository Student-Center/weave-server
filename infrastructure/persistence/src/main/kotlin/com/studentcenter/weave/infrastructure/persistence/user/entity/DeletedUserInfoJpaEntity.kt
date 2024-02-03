package com.studentcenter.weave.infrastructure.persistence.user.entity

import com.studentcenter.weave.domain.user.entity.DeletedUserInfo
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
@Table(name = "deleted_user_info")
class DeletedUserInfoJpaEntity (
    id: UUID,
    email: Email,
    socialLoginProvider: SocialLoginProvider,
    reason: String? = null,
    registeredAt: LocalDateTime,
    deletedAt: LocalDateTime
){

    @Id
    var id: UUID = id
        private set

    @Column(nullable = false)
    var email: Email = email
        private set

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(value = EnumType.STRING)
    var socialLoginProvider: SocialLoginProvider = socialLoginProvider
        private set

    @Column(nullable = true)
    var reason: String? = reason
        private set

    @Column(nullable = false)
    var registeredAt: LocalDateTime = registeredAt
        private set

    @Column(nullable = false)
    var deletedAt: LocalDateTime = deletedAt
        private set

    companion object {
        fun DeletedUserInfo.toJpaEntity(): DeletedUserInfoJpaEntity {
            return DeletedUserInfoJpaEntity(
                id = id,
                email = email,
                socialLoginProvider = socialLoginProvider,
                reason = reason,
                registeredAt = registeredAt,
                deletedAt = deletedAt
            )
        }
    }

}
