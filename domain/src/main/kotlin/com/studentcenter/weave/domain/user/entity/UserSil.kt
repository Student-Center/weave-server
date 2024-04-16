package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.domain.common.DomainEntity
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.util.*

/**
 * 유저의 실(재화) 정보
 */
data class UserSil(
    override val id: UUID = UuidCreator.create(),
    val userId: UUID,
    val amount: Long = 0,
) : DomainEntity {

    init {
        require(amount >= 0) { "amount는 0 이상이어야 합니다." }
    }

    fun increment(amount: Long): UserSil {
        return copy(amount = this.amount + amount)
    }

    companion object {

        fun create(userId: UUID): UserSil {
            return UserSil(userId = userId)
        }
    }

}
