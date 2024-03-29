package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.UpdateMyHeight
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.vo.Height
import com.studentcenter.weave.support.common.vo.toUpdateParam
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateMyHeightService(
    private val userDomainService: UserDomainService,
    private val userSilDomainService: UserSilDomainService,
) : UpdateMyHeight {

    @Transactional
    override fun invoke(height: Height) {
        val user: User = getCurrentUserAuthentication()
            .let { userDomainService.getById(it.userId) }

        userDomainService.updateById(user.id, height.toUpdateParam())

        if (user.height == null) {
            userSilDomainService.incrementByUserId(user.id, 30)
        }
    }

}
