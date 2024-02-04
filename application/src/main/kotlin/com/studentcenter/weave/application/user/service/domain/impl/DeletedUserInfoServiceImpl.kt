package com.studentcenter.weave.application.user.service.domain.impl

import com.studentcenter.weave.application.user.port.outbound.DeletedUserInfoRepository
import com.studentcenter.weave.application.user.service.domain.DeletedUserInfoService
import com.studentcenter.weave.domain.user.entity.DeletedUserInfo
import com.studentcenter.weave.domain.user.entity.UserAuthInfo
import org.springframework.stereotype.Service

@Service
class DeletedUserInfoServiceImpl(
    private val deletedUserInfoRepository: DeletedUserInfoRepository,
) : DeletedUserInfoService {

    override fun create(
        userAuthInfo: UserAuthInfo,
        reason: String?
    ) {
        DeletedUserInfo.create(
            userAuthInfo = userAuthInfo,
            reason = reason
        ).also { deletedUserInfoRepository.save(it) }
    }

}
