package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.user.port.inbound.UserGetByIdUseCase
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserGetByIdApplicationService(
    private val userDomainService: UserDomainService,
) : UserGetByIdUseCase {

    override fun invoke(id: UUID): User {
        return userDomainService.getById(id)
    }

}
