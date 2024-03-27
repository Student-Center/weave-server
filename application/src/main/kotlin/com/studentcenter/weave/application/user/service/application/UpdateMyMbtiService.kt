package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.UpdateMyMbti
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.domain.user.vo.Mbti
import org.springframework.stereotype.Service

@Service
class UpdateMyMbtiService (
    private val userDomainService: UserDomainService,
): UpdateMyMbti {

    override fun invoke(mbti: Mbti) {
        getCurrentUserAuthentication()
            .let { userDomainService.updateById(it.userId, mbti = mbti) }

    }

}
