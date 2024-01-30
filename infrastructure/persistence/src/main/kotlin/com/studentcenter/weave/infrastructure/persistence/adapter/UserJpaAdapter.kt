package com.studentcenter.weave.infrastructure.persistence.adapter

import com.studentcenter.weave.application.port.outbound.UserRepository
import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceExceptionType
import com.studentcenter.weave.infrastructure.persistence.entity.UserJpaEntity
import com.studentcenter.weave.infrastructure.persistence.entity.UserJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.repository.UserJpaRepository
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserJpaAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun save(user: User) {
        val userJpaEntity: UserJpaEntity = user.toJpaEntity()
        userJpaRepository.save(userJpaEntity)
    }

    override fun getById(id: UUID): User {
        return userJpaRepository
            .findById(id)
            .orElseThrow {
                throw CustomException(
                    type = PersistenceExceptionType.RESOURCE_NOT_FOUND,
                    message = "유저(id: $id)를 찾을 수 없습니다."
                )
            }
            .toDomain()
    }

}
