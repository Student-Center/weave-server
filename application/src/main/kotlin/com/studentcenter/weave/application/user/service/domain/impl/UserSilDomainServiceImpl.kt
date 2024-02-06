package com.studentcenter.weave.application.user.service.domain.impl

import com.studentcenter.weave.application.user.port.outbound.UserSilRepository
import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import com.studentcenter.weave.domain.user.entity.UserSil
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserSilDomainServiceImpl(
    private val userSilRepository: UserSilRepository
) : UserSilDomainService {

    override fun create(userId: UUID): UserSil {
        return UserSil
            .create(userId)
            .also { userSilRepository.save(it) }
    }

    override fun incrementByUserId(
        userId: UUID,
        amount: Long
    ): UserSil {
        return userSilRepository
            .getByUserId(userId)
            .increment(amount)
            .also { userSilRepository.save(it) }
    }
}
