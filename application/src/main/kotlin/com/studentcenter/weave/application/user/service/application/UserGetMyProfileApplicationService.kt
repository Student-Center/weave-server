package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.user.port.inbound.UserGetMyProfileUseCase
import org.springframework.stereotype.Service

@Service
class UserGetMyProfileApplicationService (
): UserGetMyProfileUseCase {

    override fun invoke(): UserGetMyProfileUseCase.Result {
        TODO("Not yet implemented")
    }

}
