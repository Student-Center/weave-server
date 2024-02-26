package com.studentcenter.weave.infrastructure.persistence.user.adapter

import com.studentcenter.weave.application.user.port.outbound.UserRepository
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.vo.KakaoId
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceExceptionType
import com.studentcenter.weave.infrastructure.persistence.user.entity.UserJpaEntity
import com.studentcenter.weave.infrastructure.persistence.user.entity.UserJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.user.repository.UserJpaRepository
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

    override fun findByKakaoId(kakaoId: KakaoId): User? {
        return userJpaRepository
            .findByKakaoId(kakaoId.value)
            .orElse(null)
            ?.toDomain()
    }

    override fun deleteById(id: UUID) {
        userJpaRepository.deleteById(id)
    }

}
