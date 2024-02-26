package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.UserSetMyKakaoIdUseCase
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.vo.KakaoId
import com.studentcenter.weave.support.common.vo.toUpdateParam
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserSetMyKakaoIdApplicationService(
    private val userDomainService: UserDomainService,
    private val userSilDomainService: UserSilDomainService,
) : UserSetMyKakaoIdUseCase {

    @Transactional
    override fun invoke(kakaoId: KakaoId) {
        val user: User = getCurrentUserAuthentication()
            .userId
            .also { checkIfKakaoIdAlreadyRegistered(kakaoId, it) }
            .let { userDomainService.getById(it) }

        userDomainService.updateById(
            id = user.id,
            kakaoId = kakaoId.toUpdateParam()
        )

        if (user.kakaoId == null) {
            userSilDomainService.incrementByUserId(user.id, 30)
        }
    }

    private fun checkIfKakaoIdAlreadyRegistered(
        kakaoId: KakaoId,
        userId: UUID,
    ) {
        userDomainService.findByKakaoId(kakaoId)
            ?.takeIf { it.id != userId }
            ?.let { throw IllegalArgumentException("이미 등록된 카카오 아이디입니다.") }
    }

}
