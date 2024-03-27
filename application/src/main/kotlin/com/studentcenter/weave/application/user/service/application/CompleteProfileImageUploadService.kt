package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.CompleteProfileImageUpload
import com.studentcenter.weave.application.user.port.outbound.UserProfileImageUrlPort
import com.studentcenter.weave.application.user.port.outbound.UserRepository
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CompleteProfileImageUploadService(
    private val userProfileImageUrlPort: UserProfileImageUrlPort,
    private val userRepository: UserRepository,
) : CompleteProfileImageUpload {

    @Transactional
    override fun invoke(command: CompleteProfileImageUpload.Command) {
        val user: User = getCurrentUserAuthentication()
            .userId
            .let { userRepository.getById(it) }

        user.updateProfileImage(
            imageId = command.imageId,
            extension = command.extension,
            getProfileImageSourceAction = userProfileImageUrlPort::findByIdAndExtension,
        ).also {
            userRepository.save(it)
        }
    }

}
