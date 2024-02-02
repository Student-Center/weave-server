package com.studentcenter.weave.bootstrap.user.controller

import com.studentcenter.weave.application.user.port.inbound.UserRegisterUseCase
import com.studentcenter.weave.application.user.port.inbound.UserUnregisterUseCase
import com.studentcenter.weave.application.user.vo.UserTokenClaims
import com.studentcenter.weave.bootstrap.user.api.UserApi
import com.studentcenter.weave.bootstrap.user.dto.UserGetMyProfileResponse
import com.studentcenter.weave.bootstrap.user.dto.UserRegisterRequest
import com.studentcenter.weave.bootstrap.user.dto.UserRegisterResponse
import com.studentcenter.weave.domain.university.vo.MajorName
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Url
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserRestController(
    private val userRegisterUseCase: UserRegisterUseCase,
    private val userUnregisterUseCase: UserUnregisterUseCase,
) : UserApi {

    override fun register(
        registerTokenClaim: UserTokenClaims.RegisterToken,
        request: UserRegisterRequest
    ): ResponseEntity<UserRegisterResponse> {
        val command: UserRegisterUseCase.Command =
            UserRegisterUseCase.Command(
                nickname = registerTokenClaim.nickname,
                email = registerTokenClaim.email,
                socialLoginProvider = registerTokenClaim.socialLoginProvider,
                gender = request.gender,
                mbti = request.mbti,
                birthYear = request.birthYear,
                universityId = request.universityId,
                majorId = request.majorId,
            )

        return when (val result: UserRegisterUseCase.Result =
            userRegisterUseCase.invoke(command)) {
            is UserRegisterUseCase.Result.Success -> {
                val body = UserRegisterResponse.Success(
                    accessToken = result.accessToken,
                    refreshToken = result.refreshToken,
                )
                ResponseEntity.status(HttpStatus.CREATED).body(body)
            }
        }
    }

    override fun unregister() {
        userUnregisterUseCase.invoke()
    }

    override fun getMyProfile(): UserGetMyProfileResponse {
        return UserGetMyProfileResponse(
            id = UuidCreator.create(),
            nickname = Nickname("test"),
            birthYear = BirthYear(1999),
            majorName = MajorName("컴퓨터 공학과"),
            avatar = Url("https://test.com"),
            mbti = Mbti("INFP"),
            animalType = null,
            height = null,
            isUniversityEmailVerified = false,
        )
    }

}
