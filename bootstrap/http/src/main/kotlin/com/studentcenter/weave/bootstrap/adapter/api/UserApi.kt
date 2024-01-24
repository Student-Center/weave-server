package com.studentcenter.weave.bootstrap.adapter.api

import com.studentcenter.weave.bootstrap.adapter.dto.RegisterUserRequest
import com.studentcenter.weave.bootstrap.adapter.dto.RegisterUserResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping


@Tag(name = "User", description = "User API")
@RequestMapping("/api/user", produces = ["application/json;charset=utf-8"])
interface UserApi {

    @Operation(summary = "Register User")
    @PostMapping
    fun register(
        @RequestHeader("Register-Token")
        authToken: String,
        @RequestBody
        request: RegisterUserRequest
    ): RegisterUserResponse

}
