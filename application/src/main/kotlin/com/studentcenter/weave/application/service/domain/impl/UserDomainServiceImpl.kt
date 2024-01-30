package com.studentcenter.weave.application.service.domain.impl

import com.studentcenter.weave.application.port.outbound.UserRepository
import com.studentcenter.weave.application.service.domain.UserDomainService
import com.studentcenter.weave.domain.entity.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDomainServiceImpl (
    private val userRepository: UserRepository
): UserDomainService {

    override fun getById(id: UUID): User {
        TODO("Not yet implemented")
    }

    override fun save(user: User) {
        userRepository.save(user)
    }
}
