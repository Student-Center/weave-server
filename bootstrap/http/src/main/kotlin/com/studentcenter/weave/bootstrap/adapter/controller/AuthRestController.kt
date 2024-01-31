package com.studentcenter.weave.bootstrap.adapter.controller

import com.studentcenter.weave.application.port.inbound.UserRefreshTokenUseCase
import com.studentcenter.weave.application.port.inbound.UserSocialLoginUseCase
import com.studentcenter.weave.bootstrap.adapter.api.AuthApi
import com.studentcenter.weave.bootstrap.adapter.dto.RefreshLoginTokenResponse
import com.studentcenter.weave.bootstrap.adapter.dto.RefreshTokenRequest
import com.studentcenter.weave.bootstrap.adapter.dto.SocialLoginRequest
import com.studentcenter.weave.bootstrap.adapter.dto.SocialLoginResponse
import com.studentcenter.weave.domain.enum.SocialLoginProvider
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthRestController(
    private val socialLoginUseCase: UserSocialLoginUseCase,
    private val userRefreshTokenUseCase: UserRefreshTokenUseCase,
) : AuthApi {

    override fun socialLogin(
        provider: SocialLoginProvider,
        request: SocialLoginRequest,
    ): ResponseEntity<SocialLoginResponse> {
        val command: UserSocialLoginUseCase.Command = UserSocialLoginUseCase.Command(
            socialLoginProvider = provider,
            idToken = request.idToken,
        )
        return when (
            val result: UserSocialLoginUseCase.Result = socialLoginUseCase.invoke(command)
        ) {
            is UserSocialLoginUseCase.Result.Success -> {
                val body = SocialLoginResponse.Success(
                    accessToken = result.accessToken,
                    refreshToken = result.refreshToken,
                )
                ResponseEntity
                    .ok(body)
            }

            is UserSocialLoginUseCase.Result.NotRegistered -> {
                val body = SocialLoginResponse.UserNotRegistered(
                    registerToken = result.registerToken,
                )
                ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(body)
            }
        }
    }

    override fun refreshLoginToken(
        request: RefreshTokenRequest,
    ): RefreshLoginTokenResponse {
        val command: UserRefreshTokenUseCase.Command =
            UserRefreshTokenUseCase.Command(request.refreshToken)

        return userRefreshTokenUseCase
            .invoke(command)
            .let {
                RefreshLoginTokenResponse(
                    accessToken = it.accessToken,
                    refreshToken = it.refreshToken,
                )
            }
    }
}
