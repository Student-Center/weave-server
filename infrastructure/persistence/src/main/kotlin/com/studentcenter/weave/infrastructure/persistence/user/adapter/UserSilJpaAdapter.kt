package com.studentcenter.weave.infrastructure.persistence.user.adapter

import com.studentcenter.weave.application.user.port.outbound.UserSilRepository
import com.studentcenter.weave.domain.user.entity.UserSil
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceExceptionType
import com.studentcenter.weave.infrastructure.persistence.user.entity.UserSilJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.user.repository.UserSilJpaRepository
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserSilJpaAdapter(
    private val userSilJpaRepository: UserSilJpaRepository
) : UserSilRepository {

    override fun save(userSil: UserSil) {
        userSilJpaRepository.save(userSil.toJpaEntity())
    }

    override fun getByUserId(userId: UUID): UserSil {
        return userSilJpaRepository
            .findByUserId(userId)
            ?.toDomain()
            ?: throw CustomException(
                type = PersistenceExceptionType.RESOURCE_NOT_FOUND,
                message = "UserSil not found by userId: $userId"
            )

    }
}
