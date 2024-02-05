package com.studentcenter.weave.bootstrap.user.controller

import com.studentcenter.weave.application.user.port.inbound.UserGetMyProfileUseCase
import com.studentcenter.weave.application.user.port.inbound.UserModifyMyMbtiUseCase
import com.studentcenter.weave.application.user.port.inbound.UserRegisterUseCase
import com.studentcenter.weave.application.user.port.inbound.UserSetMyAnimalTypeUseCase
import com.studentcenter.weave.application.user.port.inbound.UserSetMyHeightUseCase
import com.studentcenter.weave.application.user.port.inbound.UserUnregisterUseCase
import com.studentcenter.weave.application.user.vo.UserTokenClaims
import com.studentcenter.weave.bootstrap.user.api.UserApi
import com.studentcenter.weave.bootstrap.user.dto.UserGetMyProfileResponse
import com.studentcenter.weave.bootstrap.user.dto.UserModifyMyMbtiRequest
import com.studentcenter.weave.bootstrap.user.dto.UserRegisterRequest
import com.studentcenter.weave.bootstrap.user.dto.UserRegisterResponse
import com.studentcenter.weave.bootstrap.user.dto.UserSetMyAnimalTypeRequest
import com.studentcenter.weave.bootstrap.user.dto.UserSetMyHeightRequest
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Height
import com.studentcenter.weave.domain.user.vo.Mbti
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserRestController(
    private val userRegisterUseCase: UserRegisterUseCase,
    private val userUnregisterUseCase: UserUnregisterUseCase,
    private val userGetMyProfileUseCase: UserGetMyProfileUseCase,
    private val userSetMyHeightUseCase: UserSetMyHeightUseCase,
    private val userSetMyAnimalTypeUseCase: UserSetMyAnimalTypeUseCase,
    private val userModifyMyMbtiUseCase: UserModifyMyMbtiUseCase,
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

    override fun setHeight(request: UserSetMyHeightRequest) {
        request.height
            .let { Height(it) }
            .let { userSetMyHeightUseCase.invoke(it) }
    }

    override fun setMyAnimalType(request: UserSetMyAnimalTypeRequest) {
        request.animalType
            .let { userSetMyAnimalTypeUseCase.invoke(it) }
    }

    override fun modifyMyMbti(request: UserModifyMyMbtiRequest) {
        Mbti(request.mbti)
            .let { userModifyMyMbtiUseCase.invoke(it) }
    }

}
