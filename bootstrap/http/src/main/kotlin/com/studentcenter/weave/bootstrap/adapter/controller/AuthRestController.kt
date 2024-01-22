package com.studentcenter.weave.bootstrap.adapter.controller

import com.studentcenter.weave.bootstrap.adapter.api.AuthApi
import com.studentcenter.weave.bootstrap.adapter.dto.RefreshLoginTokenResponse
import com.studentcenter.weave.bootstrap.adapter.dto.SocialLoginResponse
import com.studentcenter.weave.domain.enum.SocialLoginProvider
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthRestController: AuthApi {
    override fun socialLogin(
        provider: SocialLoginProvider,
        code: String,
    ): SocialLoginResponse {
        return SocialLoginResponse.Success(
            accessToken =  "test_access_token",
            refreshToken = "test_refresh_token",
        )
    }

    override fun refreshLoginToken(refreshToken: String): RefreshLoginTokenResponse {
        return RefreshLoginTokenResponse(
            accessToken =  "test_access_token",
            refreshToken = "test_refresh_token",
        )
    }

}
