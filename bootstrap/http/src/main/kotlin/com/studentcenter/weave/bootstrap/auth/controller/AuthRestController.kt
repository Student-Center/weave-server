package com.studentcenter.weave.bootstrap.auth.controller

import com.studentcenter.weave.application.user.port.inbound.Logout
import com.studentcenter.weave.application.user.port.inbound.RefreshToken
import com.studentcenter.weave.application.user.port.inbound.SocialLogin
import com.studentcenter.weave.bootstrap.auth.api.AuthApi
import com.studentcenter.weave.bootstrap.auth.dto.RefreshLoginTokenResponse
import com.studentcenter.weave.bootstrap.auth.dto.RefreshTokenRequest
import com.studentcenter.weave.bootstrap.auth.dto.SocialLoginRequest
import com.studentcenter.weave.bootstrap.auth.dto.SocialLoginResponse
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthRestController(
    private val socialLoginUseCase: SocialLogin,
    private val refreshToken: RefreshToken,
    private val logoutUser: Logout,
) : AuthApi {

    override fun socialLogin(
        provider: SocialLoginProvider,
        request: SocialLoginRequest,
    ): ResponseEntity<SocialLoginResponse> {
        val command: SocialLogin.Command = SocialLogin.Command(
            socialLoginProvider = provider,
            idToken = request.idToken,
        )
        return when (
            val result: SocialLogin.Result = socialLoginUseCase.invoke(command)
        ) {
            is SocialLogin.Result.Success -> {
                val body = SocialLoginResponse.Success(
                    accessToken = result.accessToken,
                    refreshToken = result.refreshToken,
                )
                ResponseEntity
                    .ok(body)
            }

            is SocialLogin.Result.NotRegistered -> {
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
        val command: RefreshToken.Command =
            RefreshToken.Command(request.refreshToken)

        return refreshToken
            .invoke(command)
            .let {
                RefreshLoginTokenResponse(
                    accessToken = it.accessToken,
                    refreshToken = it.refreshToken,
                )
            }
    }

    override fun logout() {
        logoutUser.invoke()
    }
}
