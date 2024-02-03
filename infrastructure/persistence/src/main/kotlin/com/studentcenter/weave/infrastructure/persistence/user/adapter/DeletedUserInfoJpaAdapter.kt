package com.studentcenter.weave.infrastructure.persistence.user.adapter

import com.studentcenter.weave.application.user.port.outbound.DeletedUserInfoRepository
import com.studentcenter.weave.domain.user.entity.DeletedUserInfo
import com.studentcenter.weave.infrastructure.persistence.user.entity.DeletedUserInfoJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.user.repository.DeletedUserInfoJpaRepository
import org.springframework.stereotype.Component

@Component
class DeletedUserInfoJpaAdapter (
    private val deletedUserInfoJpaRepository: DeletedUserInfoJpaRepository
): DeletedUserInfoRepository {

    override fun save(deletedUserInfo: DeletedUserInfo) {
        deletedUserInfo
            .toJpaEntity()
            .let { deletedUserInfoJpaRepository.save(it) }
    }


}
