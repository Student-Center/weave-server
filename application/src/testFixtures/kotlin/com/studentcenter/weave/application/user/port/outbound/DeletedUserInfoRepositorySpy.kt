package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.DeletedUserInfo
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import com.studentcenter.weave.support.common.vo.Email
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class DeletedUserInfoRepositorySpy : DeletedUserInfoRepository {

    private val bucket = ConcurrentHashMap<UUID, DeletedUserInfo>()

    override fun save(deletedUserInfo: DeletedUserInfo) {
        bucket[deletedUserInfo.id] = deletedUserInfo
    }

    fun findByEmailAndSocialLoginProvider(
        email: Email,
        socialLoginProvider: SocialLoginProvider
    ): DeletedUserInfo? {
        return bucket.values.find { it.email == email && it.socialLoginProvider == socialLoginProvider }
    }

    fun clear() {
        bucket.clear()
    }

}
