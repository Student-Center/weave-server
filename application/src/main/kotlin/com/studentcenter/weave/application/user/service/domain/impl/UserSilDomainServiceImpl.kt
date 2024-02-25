package com.studentcenter.weave.application.user.service.domain.impl

import com.studentcenter.weave.support.lock.distributedLock
import com.studentcenter.weave.application.user.port.outbound.UserSilRepository
import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import com.studentcenter.weave.domain.user.entity.UserSil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserSilDomainServiceImpl(
    private val userSilRepository: UserSilRepository
) : UserSilDomainService {

    @Transactional
    override fun create(userId: UUID): UserSil {
        return UserSil
            .create(userId)
            .also { userSilRepository.save(it) }
    }

    override fun incrementByUserId(
        userId: UUID,
        amount: Long
    ): UserSil = distributedLock("UseSilDomainService.incrementByUserId:$userId") {
        return@distributedLock userSilRepository
            .getByUserId(userId)
            .increment(amount)
            .also { userSilRepository.save(it) }
    }

    @Transactional(readOnly = true)
    override fun getByUserId(userId: UUID): UserSil {
        return userSilRepository.getByUserId(userId)
    }

    override fun save(userSil: UserSil) {
        userSilRepository.save(userSil)
    }

}
