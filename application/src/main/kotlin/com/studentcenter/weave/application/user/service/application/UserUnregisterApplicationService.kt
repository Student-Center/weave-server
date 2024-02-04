package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.UserUnregisterUseCase
import com.studentcenter.weave.application.user.service.domain.DeletedUserInfoService
import com.studentcenter.weave.application.user.service.domain.UserAuthInfoDomainService
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserUnregisterApplicationService(
    private val userDomainService: UserDomainService,
    private val userAuthInfoDomainService: UserAuthInfoDomainService,
    private val deletedUserInfoDomainService: DeletedUserInfoService,
) : UserUnregisterUseCase {

    @Transactional
    override fun invoke(command: UserUnregisterUseCase.Command) {
        getCurrentUserAuthentication()
            .let { userAuthInfoDomainService.getByUserId(it.userId) }
            .also { userDomainService.deleteById(it.userId) }
            .also { userAuthInfoDomainService.deleteById(it.id) }
            .let { deletedUserInfoDomainService.create(it) }
    }

}
