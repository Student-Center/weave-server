package com.studentcenter.weave.infrastructure.persistence.user.adapter

import com.studentcenter.weave.application.user.port.outbound.UserAuthInfoRepository
import com.studentcenter.weave.domain.user.entity.UserAuthInfo
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceExceptionType
import com.studentcenter.weave.infrastructure.persistence.user.entity.UserAuthInfoJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.user.repository.UserAuthInfoJpaRepository
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserAuthInfoJpaAdapter(
    private val userAuthInfoJpaRepository: UserAuthInfoJpaRepository
) : UserAuthInfoRepository {

    override fun findByEmail(email: Email): UserAuthInfo? {
        return userAuthInfoJpaRepository.findByEmail(email)?.toDomain()
    }

    override fun save(userAuthInfo: UserAuthInfo) {
        userAuthInfo
            .toJpaEntity()
            .let { userAuthInfoJpaRepository.save(it) }
    }

    override fun getByUserId(userId: UUID): UserAuthInfo {
        return userAuthInfoJpaRepository
            .findByUserId(userId)
            ?.toDomain() ?: throw CustomException(
                type = PersistenceExceptionType.RESOURCE_NOT_FOUND,
                message = "UserAuthInfo not found by userId: $userId"
            )
    }

    override fun deleteById(id: UUID) {
        userAuthInfoJpaRepository.deleteById(id)
    }

}
