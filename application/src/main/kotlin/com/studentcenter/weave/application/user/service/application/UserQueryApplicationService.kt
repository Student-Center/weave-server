package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.application.user.service.domain.UserUniversityVerificationInfoDomainService
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserQueryApplicationService(
    private val userDomainService: UserDomainService,
    private val userUniversityVerificationInfoDomainService: UserUniversityVerificationInfoDomainService,
) : UserQueryUseCase {

    override fun getById(id: UUID): User {
        return userDomainService.getById(id)
    }

    override fun isUserUniversityVerified(userId: UUID): Boolean {
        return userUniversityVerificationInfoDomainService.existsByUserId(userId)
    }

}
