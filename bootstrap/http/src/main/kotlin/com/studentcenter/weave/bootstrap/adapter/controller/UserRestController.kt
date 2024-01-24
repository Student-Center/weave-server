package com.studentcenter.weave.bootstrap.adapter.controller

import com.studentcenter.weave.bootstrap.adapter.api.UserApi
import com.studentcenter.weave.bootstrap.adapter.dto.RegisterUserRequest
import com.studentcenter.weave.bootstrap.adapter.dto.RegisterUserResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class UserRestController : UserApi {

    override fun register(
        authToken: String,
        request: RegisterUserRequest
    ): RegisterUserResponse {
        return RegisterUserResponse(
            accessToken = "accessToken",
            refreshToken = "refreshToken",
        )
    }

}
