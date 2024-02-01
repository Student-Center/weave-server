package com.studentcenter.weave.infrastructure.persistence.adapter

import com.studentcenter.weave.application.port.outbound.UserAuthInfoRepository
import com.studentcenter.weave.domain.entity.UserAuthInfo
import com.studentcenter.weave.infrastructure.persistence.entity.UserAuthInfoJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.repository.UserAuthInfoJpaRepository
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Component

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

}