package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.UserSetMyKakaoIdUseCase
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.support.common.vo.toUpdateParam
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserSetMyKakaoIdApplicationService(
    private val userDomainService: UserDomainService,
    private val userSilDomainService: UserSilDomainService,
) : UserSetMyKakaoIdUseCase {

    @Transactional
    override fun invoke(kakaoId: String) {
        checkIfKakaoIdAlreadyRegistered(kakaoId)
        getCurrentUserAuthentication()
            .userId
            .let { userDomainService.getById(it) }
            .also {
                checkIfUserAlreadyRegisteredKakaoId(it)
                userDomainService.updateById(
                    id = it.id,
                    kakaoId = kakaoId.toUpdateParam()
                )
                userSilDomainService.incrementByUserId(
                    userId = it.id,
                    amount = 30
                )
            }
    }

    private fun checkIfKakaoIdAlreadyRegistered(kakaoId: String) {
        require(userDomainService.findByKakaoId(kakaoId) == null) {
            "이미 등록된 카카오 아이디에요!"
        }
    }

    private fun checkIfUserAlreadyRegisteredKakaoId(user: User) {
        require(user.kakaoId == null) {
            "이미 카카오 아이디를 등록했어요!"
        }
    }

}
