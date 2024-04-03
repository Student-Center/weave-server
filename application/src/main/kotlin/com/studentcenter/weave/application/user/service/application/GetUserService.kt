package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.application.user.port.outbound.UserRepository
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetUserService(
    private val userRepository: UserRepository,
) : GetUser {

    override fun getById(id: UUID): User {
        return userRepository.getById(id)
    }

    override fun getAllByIds(ids: List<UUID>): List<User> {
        return userRepository.getAllByIds(ids)
    }

}
