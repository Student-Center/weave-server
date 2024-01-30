package com.studentcenter.weave.bootstrap.adapter.controller

import com.studentcenter.weave.application.port.inbound.UserRegisterUseCase
import com.studentcenter.weave.application.vo.UserTokenClaims
import com.studentcenter.weave.bootstrap.adapter.api.UserApi
import com.studentcenter.weave.bootstrap.adapter.dto.RegisterUserRequest
import com.studentcenter.weave.bootstrap.adapter.dto.RegisterUserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserRestController(
    private val userRegisterUseCase: UserRegisterUseCase,
) : UserApi {

    override fun register(
        registerTokenClaim: UserTokenClaims.RegisterToken,
        request: RegisterUserRequest
    ): ResponseEntity<RegisterUserResponse> {
        val command: UserRegisterUseCase.Command = UserRegisterUseCase.Command(
            nickname = registerTokenClaim.nickname,
            email = registerTokenClaim.email,
            socialLoginProvider = registerTokenClaim.socialLoginProvider,
            gender = request.gender,
            mbti = request.mbti,
            birthYear = request.birthYear,
            universityId = request.universityId,
            majorId = request.majorId,
        )

        return when (val result: UserRegisterUseCase.Result = userRegisterUseCase.invoke(command)) {
            is UserRegisterUseCase.Result.Success -> {
                val body = RegisterUserResponse.Success(
                    accessToken = result.accessToken,
                    refreshToken = result.refreshToken,
                )
                ResponseEntity.ok(body)
            }
        }
    }

}
