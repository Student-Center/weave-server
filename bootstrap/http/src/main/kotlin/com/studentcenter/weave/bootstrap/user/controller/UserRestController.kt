package com.studentcenter.weave.bootstrap.user.controller

import com.studentcenter.weave.application.user.port.inbound.CompleteProfileImageUpload
import com.studentcenter.weave.application.user.port.inbound.GetMyProfile
import com.studentcenter.weave.application.user.port.inbound.GetProfileImageUploadUrl
import com.studentcenter.weave.application.user.port.inbound.UserModifyMyMbtiUseCase
import com.studentcenter.weave.application.user.port.inbound.UserRegisterUseCase
import com.studentcenter.weave.application.user.port.inbound.UserSendVerificationNumberEmailUseCase
import com.studentcenter.weave.application.user.port.inbound.UserSetMyAnimalTypeUseCase
import com.studentcenter.weave.application.user.port.inbound.UserSetMyHeightUseCase
import com.studentcenter.weave.application.user.port.inbound.UserSetMyKakaoIdUseCase
import com.studentcenter.weave.application.user.port.inbound.UserUnregisterUseCase
import com.studentcenter.weave.application.user.port.inbound.UserVerifyVerificationNumberUseCase
import com.studentcenter.weave.application.user.vo.UserTokenClaims
import com.studentcenter.weave.application.user.vo.UserUniversityVerificationNumber
import com.studentcenter.weave.bootstrap.user.api.UserApi
import com.studentcenter.weave.bootstrap.user.dto.UserCompleteProfileImageUploadRequest
import com.studentcenter.weave.bootstrap.user.dto.UserGetAvailableAnimalTypesResponse
import com.studentcenter.weave.bootstrap.user.dto.UserGetMyProfileResponse
import com.studentcenter.weave.bootstrap.user.dto.UserGetProfileImageUploadUrlResponse
import com.studentcenter.weave.bootstrap.user.dto.UserModifyMyMbtiRequest
import com.studentcenter.weave.bootstrap.user.dto.UserRegisterRequest
import com.studentcenter.weave.bootstrap.user.dto.UserRegisterResponse
import com.studentcenter.weave.bootstrap.user.dto.UserSetMyAnimalTypeRequest
import com.studentcenter.weave.bootstrap.user.dto.UserSetMyHeightRequest
import com.studentcenter.weave.bootstrap.user.dto.UserSetMyKakaoIdRequest
import com.studentcenter.weave.bootstrap.user.dto.UserUnivVerificationSendRequest
import com.studentcenter.weave.bootstrap.user.dto.UserUnivVerificationVerifyRequest
import com.studentcenter.weave.domain.user.entity.UserProfileImage
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Height
import com.studentcenter.weave.domain.user.vo.KakaoId
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserRestController(
    private val userRegisterUseCase: UserRegisterUseCase,
    private val userUnregisterUseCase: UserUnregisterUseCase,
    private val getMyProfileUseCase: GetMyProfile,
    private val userSetMyHeightUseCase: UserSetMyHeightUseCase,
    private val userSetMyAnimalTypeUseCase: UserSetMyAnimalTypeUseCase,
    private val userModifyMyMbtiUseCase: UserModifyMyMbtiUseCase,
    private val userSendVerificationNumberEmailUseCase: UserSendVerificationNumberEmailUseCase,
    private val userVerifyVerificationNumberUseCase: UserVerifyVerificationNumberUseCase,
    private val userSetMyKakaoIdUseCase: UserSetMyKakaoIdUseCase,
    private val userGetProfileImageUploadUrlUseCase: GetProfileImageUploadUrl,
    private val completeProfileImageUpload: CompleteProfileImageUpload,
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
        return getMyProfileUseCase
            .invoke()
            .let {
                UserGetMyProfileResponse(
                    id = it.id,
                    nickname = it.nickname.value,
                    birthYear = it.birthYear.value,
                    universityName = it.universityName.value,
                    majorName = it.majorName.value,
                    avatar = it.avatar?.value,
                    mbti = it.mbti.value,
                    animalType = it.animalType?.description,
                    height = it.height?.value,
                    kakaoId = it.kakaoId?.value,
                    isUniversityEmailVerified = it.isUniversityEmailVerified,
                    sil = it.sil,
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

    override fun setMyKakaoId(request: UserSetMyKakaoIdRequest) {
        userSetMyKakaoIdUseCase.invoke(KakaoId(request.kakaoId))
    }

    override fun sendEmailVerificationNumber(request: UserUnivVerificationSendRequest) {
        userSendVerificationNumberEmailUseCase.invoke(Email(request.universityEmail))
    }

    override fun verifyVerificationNumber(request: UserUnivVerificationVerifyRequest) {
        userVerifyVerificationNumberUseCase.invoke(
            command = UserVerifyVerificationNumberUseCase.Command(
                universityEmail = Email(request.universityEmail),
                verificationNumber = UserUniversityVerificationNumber(request.verificationNumber),
            )
        )
    }

    override fun getProfileImageUploadUrl(extension: UserProfileImage.Extension): UserGetProfileImageUploadUrlResponse {
        return userGetProfileImageUploadUrlUseCase.invoke(extension)
            .let { UserGetProfileImageUploadUrlResponse.from(it) }
    }

    override fun completeUserProfileImageUpload(request: UserCompleteProfileImageUploadRequest) {
        completeProfileImageUpload.invoke(
            CompleteProfileImageUpload.Command(
                imageId = request.imageId,
                extension = request.extension,
            )
        )
    }

    override fun getAnimalTypes(): UserGetAvailableAnimalTypesResponse {
        return UserGetAvailableAnimalTypesResponse.create()
    }

}
