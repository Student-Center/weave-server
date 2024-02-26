package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.UserSetMyKakaoIdUseCase
import com.studentcenter.weave.application.user.port.outbound.UserKakaoAuthRepository
import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import com.studentcenter.weave.domain.user.entity.UserKakaoAuth
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserSetMyKakaoIdApplicationService(
    private val userKakaoAuthRepository: UserKakaoAuthRepository,
    private val userSilDomainService: UserSilDomainService,
) : UserSetMyKakaoIdUseCase {

    @Transactional
    override fun invoke(kakaoId: String) {
        getCurrentUserAuthentication().userId
            .let {
                checkIfUserAlreadyRegisteredKakaoId(it)
                checkIfKakaoIdAlreadyRegistered(kakaoId)
                UserKakaoAuth.create(it, kakaoId)
            }
            .also {
                userKakaoAuthRepository.save(it)
                userSilDomainService.incrementByUserId(it.userId, 30)
            }
    }

    private fun checkIfKakaoIdAlreadyRegistered(kakaoId: String) {
        require (userKakaoAuthRepository.findByKakaoId(kakaoId) == null) {
            "이미 등록된 카카오 아이디에요!"
        }
    }

    private fun checkIfUserAlreadyRegisteredKakaoId(userId: UUID) {
        require (userKakaoAuthRepository.findByUserId(userId) == null) {
            "이미 카카오 아이디를 등록 했어요!"
        }
    }

}
