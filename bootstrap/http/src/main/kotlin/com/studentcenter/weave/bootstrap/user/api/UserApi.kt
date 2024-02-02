package com.studentcenter.weave.bootstrap.user.api

import com.studentcenter.weave.application.user.vo.UserTokenClaims
import com.studentcenter.weave.bootstrap.common.security.annotation.RegisterTokenClaim
import com.studentcenter.weave.bootstrap.common.security.annotation.Secured
import com.studentcenter.weave.bootstrap.user.dto.UserGetMyProfileResponse
import com.studentcenter.weave.bootstrap.user.dto.UserRegisterRequest
import com.studentcenter.weave.bootstrap.user.dto.UserRegisterResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "User", description = "User API")
@RequestMapping("/api/users", produces = ["application/json;charset=utf-8"])
interface UserApi {

    @Operation(
        summary = "Register User",
    )
    @Parameters(
        Parameter(
            `in` = ParameterIn.HEADER,
            name = "Register-Token",
            required = true,
            schema = Schema(type = "string")
        )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun register(
        @Parameter(hidden = true)
        @RegisterTokenClaim
        registerTokenClaim: UserTokenClaims.RegisterToken,
        @RequestBody
        request: UserRegisterRequest
    ): ResponseEntity<UserRegisterResponse>

    @Secured
    @Operation(summary = "")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun unregister()

    @Secured
    @Operation(summary = "User My Page")
    @GetMapping("/my-profile")
    @ResponseStatus(HttpStatus.OK)
    fun getMyProfile(): UserGetMyProfileResponse

}
