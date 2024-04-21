package com.studentcenter.weave.infrastructure.persistence.user.adapter

import com.studentcenter.weave.application.user.port.outbound.UserAuthInfoRepository
import com.studentcenter.weave.domain.user.entity.UserAuthInfo
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceException
import com.studentcenter.weave.infrastructure.persistence.user.entity.UserAuthInfoJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.user.repository.UserAuthInfoJpaRepository
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserAuthInfoJpaAdapter(
    private val userAuthInfoJpaRepository: UserAuthInfoJpaRepository,
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
            ?.toDomain()
            ?: throw PersistenceException.ResourceNotFound("UserAuthInfo(userId: $userId)를 찾을 수 없습니다.")
    }

    override fun deleteById(id: UUID) {
        userAuthInfoJpaRepository.deleteById(id)
    }

}
