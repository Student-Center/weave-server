package com.studentcenter.weave.bootstrap.adapter.controller

import com.studentcenter.weave.bootstrap.adapter.api.AuthApi
import com.studentcenter.weave.bootstrap.adapter.dto.RefreshLoginTokenResponse
import com.studentcenter.weave.bootstrap.adapter.dto.RefreshTokenRequest
import com.studentcenter.weave.bootstrap.adapter.dto.SocialLoginRequest
import com.studentcenter.weave.bootstrap.adapter.dto.SocialLoginResponse
import com.studentcenter.weave.domain.enum.SocialLoginProvider
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthRestController : AuthApi {

    override fun socialLogin(
        provider: SocialLoginProvider,
        request: SocialLoginRequest,
    ): ResponseEntity<SocialLoginResponse> {
        val success = SocialLoginResponse.Success(
            accessToken = "test_access_token",
            refreshToken = "test_refresh_token",
        )
        return ResponseEntity.ok(success)
    }

    override fun refreshLoginToken(
        request: RefreshTokenRequest,
    ): RefreshLoginTokenResponse {
        return RefreshLoginTokenResponse(
            accessToken = "test_access_token",
            refreshToken = "test_refresh_token",
        )
    }

}
