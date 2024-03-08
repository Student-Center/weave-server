package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.exception.UserExceptionType
import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.UserCompleteProfileImageUploadUseCase
import com.studentcenter.weave.application.user.port.outbound.UserProfileImageUrlPort
import com.studentcenter.weave.application.user.port.outbound.UserRepository
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.vo.Url
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserCompleteProfileImageUploadApplicationService(
    private val userProfileImageUrlPort: UserProfileImageUrlPort,
    private val userRepository: UserRepository,
) : UserCompleteProfileImageUploadUseCase {

    @Transactional
    override fun invoke() {
        val user: User = getCurrentUser()
        val profileImageUrls: List<Url> = getUserProfileImageUrls(user)

        when {
            profileImageUrls.isEmpty() -> throwCustomException()
            profileImageUrls.size == 1 -> handleSingleProfileImage(user, profileImageUrls)
            profileImageUrls.size == 2 -> handleTwoProfileImages(user, profileImageUrls)
            else -> handleMultipleProfileImages(user, profileImageUrls)
        }
    }

    private fun getCurrentUser(): User {
        val userId = getCurrentUserAuthentication().userId
        return userRepository.getById(userId)
    }

    private fun getUserProfileImageUrls(user: User): List<Url> {
        val profileImageUrls: List<Url> = userProfileImageUrlPort.findAllByUserId(user.id)
        if (profileImageUrls.isEmpty()) {
            throwCustomException()
        }
        return profileImageUrls
    }

    private fun handleSingleProfileImage(user: User, profileImageUrls: List<Url>) {
        if (user.avatar == null) {
            user.updateAvatar(profileImageUrls.first())
                .also { userRepository.save(it) }
        } else if (profileImageUrls.first() != user.avatar) {
            deleteProfileImageUrls(profileImageUrls)
            throwCustomException()
        }
    }

    private fun handleTwoProfileImages(user: User, profileImageUrls: List<Url>) {
        if (user.avatar == null || profileImageUrls.contains(user.avatar).not()) {
            deleteProfileImageUrls(profileImageUrls)
            throwCustomException()
            return
        }

        val newAvatar = profileImageUrls.first { it != user.avatar }
        userProfileImageUrlPort.deleteByUrl(user.avatar!!)
        user.updateAvatar(newAvatar)
            .also { userRepository.save(it) }
    }

    private fun handleMultipleProfileImages(user: User, profileImageUrls: List<Url>) {
        val imagesToDelete = profileImageUrls.filter { it != user.avatar }
        deleteProfileImageUrls(imagesToDelete)
        throwCustomException()
    }

    private fun deleteProfileImageUrls(profileImageUrls: List<Url>) {
        profileImageUrls.forEach { userProfileImageUrlPort.deleteByUrl(it) }
    }

    private fun throwCustomException() {
        throw CustomException(
            type = UserExceptionType.USER_PROFILE_IMAGE_UPLOAD_FAILED,
            message = "프로필 이미지가 정상적으로 업로드 되지 않았습니다. 다시시도해주세요"
        )
    }
}
