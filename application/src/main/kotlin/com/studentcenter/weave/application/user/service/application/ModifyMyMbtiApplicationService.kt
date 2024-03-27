package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.ModifyMyMbti
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.domain.user.vo.Mbti
import org.springframework.stereotype.Service

@Service
class ModifyMyMbtiApplicationService (
    private val userDomainService: UserDomainService,
): ModifyMyMbti {

    override fun invoke(mbti: Mbti) {
        getCurrentUserAuthentication()
            .let { userDomainService.updateById(it.userId, mbti = mbti) }

    }

}
