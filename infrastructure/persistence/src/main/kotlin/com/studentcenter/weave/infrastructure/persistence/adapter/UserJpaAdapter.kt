package com.studentcenter.weave.infrastructure.persistence.adapter

import com.studentcenter.weave.application.port.outbound.UserRepository
import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.infrastructure.persistence.entity.UserJpaEntity
import com.studentcenter.weave.infrastructure.persistence.entity.UserJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.repository.UserJpaRepository
import org.springframework.stereotype.Component

@Component
class UserJpaAdapter (
    private val userJpaRepository: UserJpaRepository
): UserRepository {

    override fun save(user: User) {
        val userJpaEntity: UserJpaEntity = user.toJpaEntity()
        userJpaRepository.save(userJpaEntity)
    }

}
