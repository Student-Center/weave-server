package com.studentcenter.weave.bootstrap.user.controller

import com.studentcenter.weave.application.user.port.inbound.UserGetMyProfileUseCase
import com.studentcenter.weave.application.user.port.inbound.UserRegisterUseCase
import com.studentcenter.weave.application.user.port.inbound.UserUnregisterUseCase
import com.studentcenter.weave.application.user.vo.UserTokenClaims
import com.studentcenter.weave.bootstrap.user.api.UserApi
import com.studentcenter.weave.bootstrap.user.dto.UserGetMyProfileResponse
import com.studentcenter.weave.bootstrap.user.dto.UserRegisterRequest
import com.studentcenter.weave.bootstrap.user.dto.UserRegisterResponse
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Mbti
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserRestController(
    private val userRegisterUseCase: UserRegisterUseCase,
    private val userUnregisterUseCase: UserUnregisterUseCase,
    private val userGetMyProfileUseCase: UserGetMyProfileUseCase,
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
                mbti = Mbti(request.mbti),
                birthYear = BirthYear(request.birthYear),
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
        UserUnregisterUseCase.Command()
            .let { userUnregisterUseCase.invoke(it) }
    }

    override fun getMyProfile(): UserGetMyProfileResponse {
        return userGetMyProfileUseCase
            .invoke()
            .let {
                UserGetMyProfileResponse(
                    id = it.id,
                    nickname = it.nickname.value,
                    birthYear = it.birthYear.value,
                    majorName = it.majorName.value,
                    avatar = it.avatar?.value,
                    mbti = it.mbti.value,
                    animalType = it.animalType,
                    height = it.height?.value,
                    isUniversityEmailVerified = it.isUniversityEmailVerified,
                )
            }
    }

}
