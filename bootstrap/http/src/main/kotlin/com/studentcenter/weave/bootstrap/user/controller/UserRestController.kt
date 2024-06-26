package com.studentcenter.weave.bootstrap.user.controller

import com.studentcenter.weave.application.user.port.inbound.CompleteProfileImageUpload
import com.studentcenter.weave.application.user.port.inbound.GetMyProfile
import com.studentcenter.weave.application.user.port.inbound.GetProfileImageUploadUrl
import com.studentcenter.weave.application.user.port.inbound.UpdateMyMbti
import com.studentcenter.weave.application.user.port.inbound.RegisterUser
import com.studentcenter.weave.application.user.port.inbound.SendVerificationEmail
import com.studentcenter.weave.application.user.port.inbound.UpdateMyAnimalType
import com.studentcenter.weave.application.user.port.inbound.UpdateMyHeight
import com.studentcenter.weave.application.user.port.inbound.UpdateMyKakaoId
import com.studentcenter.weave.application.user.port.inbound.UnregisterUser
import com.studentcenter.weave.application.user.port.inbound.VerifyUniversityVerificationNumber
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
    private val registerUser: RegisterUser,
    private val unregisterUser: UnregisterUser,
    private val getMyProfile: GetMyProfile,
    private val updateMyHeight: UpdateMyHeight,
    private val userUpdateMyAnimalType: UpdateMyAnimalType,
    private val userUpdateMyMbti: UpdateMyMbti,
    private val sendVerificationEmail: SendVerificationEmail,
    private val verifyUniversityVerificationNumber: VerifyUniversityVerificationNumber,
    private val updateMyKakaoId: UpdateMyKakaoId,
    private val userGetProfileImageUploadUrl: GetProfileImageUploadUrl,
    private val completeProfileImageUpload: CompleteProfileImageUpload,
) : UserApi {

    override fun register(
        registerTokenClaim: UserTokenClaims.RegisterToken,
        request: UserRegisterRequest
    ): ResponseEntity<UserRegisterResponse> {
        val command: RegisterUser.Command =
            RegisterUser.Command(
                nickname = registerTokenClaim.nickname,
                email = registerTokenClaim.email,
                socialLoginProvider = registerTokenClaim.socialLoginProvider,
                gender = request.gender,
                mbti = Mbti(request.mbti),
                birthYear = BirthYear(request.birthYear),
                universityId = request.universityId,
                majorId = request.majorId,
            )

        return when (val result: RegisterUser.Result =
            registerUser.invoke(command)) {
            is RegisterUser.Result.Success -> {
                val body = UserRegisterResponse.Success(
                    accessToken = result.accessToken,
                    refreshToken = result.refreshToken,
                )
                ResponseEntity.status(HttpStatus.CREATED).body(body)
            }
        }
    }

    override fun unregister() {
        UnregisterUser.Command()
            .let { unregisterUser.invoke(it) }
    }

    override fun getMyProfile(): UserGetMyProfileResponse {
        return getMyProfile
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
            .let { updateMyHeight.invoke(it) }
    }

    override fun setMyAnimalType(request: UserSetMyAnimalTypeRequest) {
        request.animalType
            .let { userUpdateMyAnimalType.invoke(it) }
    }

    override fun modifyMyMbti(request: UserModifyMyMbtiRequest) {
        Mbti(request.mbti)
            .let { userUpdateMyMbti.invoke(it) }
    }

    override fun setMyKakaoId(request: UserSetMyKakaoIdRequest) {
        updateMyKakaoId.invoke(KakaoId(request.kakaoId))
    }

    override fun sendEmailVerificationNumber(request: UserUnivVerificationSendRequest) {
        sendVerificationEmail.invoke(Email(request.universityEmail))
    }

    override fun verifyVerificationNumber(request: UserUnivVerificationVerifyRequest) {
        verifyUniversityVerificationNumber.invoke(
            command = VerifyUniversityVerificationNumber.Command(
                universityEmail = Email(request.universityEmail),
                verificationNumber = UserUniversityVerificationNumber(request.verificationNumber),
            )
        )
    }

    override fun getProfileImageUploadUrl(extension: UserProfileImage.Extension): UserGetProfileImageUploadUrlResponse {
        return userGetProfileImageUploadUrl.invoke(extension)
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
