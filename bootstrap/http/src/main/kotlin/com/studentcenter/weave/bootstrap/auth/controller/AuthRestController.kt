package com.studentcenter.weave.bootstrap.auth.controller

import com.studentcenter.weave.application.user.port.inbound.UserLogoutUseCase
import com.studentcenter.weave.application.user.port.inbound.UserRefreshTokenUseCase
import com.studentcenter.weave.application.user.port.inbound.UserSocialLoginUseCase
import com.studentcenter.weave.bootstrap.auth.api.AuthApi
import com.studentcenter.weave.bootstrap.auth.dto.RefreshLoginTokenResponse
import com.studentcenter.weave.bootstrap.auth.dto.RefreshTokenRequest
import com.studentcenter.weave.bootstrap.auth.dto.SocialLoginRequest
import com.studentcenter.weave.bootstrap.auth.dto.SocialLoginResponse
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.email.adaptor.VerificationCodeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthRestController(
    private val socialLoginUseCase: UserSocialLoginUseCase,
    private val userRefreshTokenUseCase: UserRefreshTokenUseCase,
    private val userLogoutUseCase: UserLogoutUseCase,
    private val verificationCodeService: VerificationCodeService,
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

    override fun logout() {
        userLogoutUseCase.invoke()
    }

    override fun sendVerificationCodeEmail() {
        verificationCodeService.sendVerificationCode(Email("djyou128@gmail.com"), "002345")
    }
}
