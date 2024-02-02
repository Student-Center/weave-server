package com.studentcenter.weave.bootstrap.adapter.api

import com.studentcenter.weave.bootstrap.adapter.dto.RefreshLoginTokenResponse
import com.studentcenter.weave.bootstrap.adapter.dto.RefreshTokenRequest
import com.studentcenter.weave.bootstrap.adapter.dto.SocialLoginRequest
import com.studentcenter.weave.bootstrap.adapter.dto.SocialLoginResponse
import com.studentcenter.weave.bootstrap.common.security.annotation.Secured
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "Auth", description = "Auth API")
@RequestMapping("/api/auth", produces = ["application/json;charset=utf-8"])
interface AuthApi {

    @Operation(summary = "Social Login")
    @PostMapping("/login/{provider}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "로그인 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = SocialLoginResponse.Success::class
                        )
                    ),
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "회원가입 필요",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = SocialLoginResponse.UserNotRegistered::class
                        )
                    ),
                ]
            ),
        ]
    )
    fun socialLogin(
        @PathVariable provider: SocialLoginProvider,
        @RequestBody request: SocialLoginRequest,
    ): ResponseEntity<SocialLoginResponse>

    @Operation(summary = "Refresh Login Token")
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    fun refreshLoginToken(
        @RequestBody request: RefreshTokenRequest,
    ): RefreshLoginTokenResponse


    @Secured
    @Operation(summary = "Logout")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    fun logout()

}
