package com.studentcenter.weave.infrastructure.persistence.user.entity

import com.studentcenter.weave.domain.user.entity.UserSil
import jakarta.persistence.Column
import jakarta.persistence.Id
import java.util.*

class UserSilJpaEntity(
    id: UUID,
    userId: UUID,
    amount: Long,
) {

    @Id
    @Column(nullable = false, updatable = false)
    var id: UUID = id
        private set

    @Column(nullable = false, updatable = false, unique = true)
    var userId: UUID = userId
        private set

    @Column(nullable = false)
    var amount: Long = amount
        private set

    companion object {

        fun UserSil.toJpaEntity(): UserSilJpaEntity {
            return UserSilJpaEntity(
                id = id,
                userId = userId,
                amount = amount,
            )
        }
    }

    fun toDomain(): UserSil {
        return UserSil(
            id = id,
            userId = userId,
            amount = amount,
        )
    }

}
