package com.studentcenter.weave.bootstrap.adapter.api

import com.studentcenter.weave.bootstrap.adapter.dto.RefreshLoginTokenResponse
import com.studentcenter.weave.bootstrap.adapter.dto.SocialLoginResponse
import com.studentcenter.weave.domain.enum.SocialLoginProvider
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Auth", description = "Auth API")
@RequestMapping("/api/auth", produces = ["application/json;charset=utf-8"])
interface AuthApi {

    @Operation(summary = "Social Login")
    @GetMapping("/login/oauth2/code/{provider}")
    @ResponseStatus(HttpStatus.OK)
    fun socialLogin(
        @PathVariable provider: SocialLoginProvider,
        @RequestParam code: String
    ): SocialLoginResponse

    @Operation(summary = "Refresh Login Token")
    @GetMapping("/login/refresh")
    @ResponseStatus(HttpStatus.OK)
    fun refreshLoginToken(
        @RequestParam refreshToken: String
    ): RefreshLoginTokenResponse

}
