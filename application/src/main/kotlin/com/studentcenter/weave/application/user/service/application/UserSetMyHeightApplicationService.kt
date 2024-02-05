package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.UserSetMyHeightUseCase
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.domain.user.vo.Height
import com.studentcenter.weave.support.common.vo.UpdateParam
import com.studentcenter.weave.support.common.vo.toUpdateParam
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserSetMyHeightApplicationService(
    private val userDomainService: UserDomainService,
) : UserSetMyHeightUseCase {

    @Transactional
    override fun invoke(height: Height?) {
        val updateHeight: UpdateParam<Height?> = height?.toUpdateParam() ?: UpdateParam(null)
        getCurrentUserAuthentication()
            .let { userDomainService.updateById(it.userId, height = updateHeight) }
    }

}
