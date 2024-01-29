package com.studentcenter.weave.application.service.application

import com.studentcenter.weave.application.port.inbound.UserRegisterUseCase
import org.springframework.stereotype.Service

@Service
class UserRegisterApplicationService : UserRegisterUseCase {

    override fun invoke(command: UserRegisterUseCase.Command): UserRegisterUseCase.Result {
        return UserRegisterUseCase.Result.Success(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }

}
