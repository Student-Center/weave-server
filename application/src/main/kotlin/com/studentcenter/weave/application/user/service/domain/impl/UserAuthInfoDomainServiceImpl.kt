package com.studentcenter.weave.application.user.service.domain.impl

import com.studentcenter.weave.application.user.port.outbound.UserAuthInfoRepository
import com.studentcenter.weave.application.user.service.domain.UserAuthInfoDomainService
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserAuthInfo
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserAuthInfoDomainServiceImpl(
    private val userAuthInfoRepository: UserAuthInfoRepository
) : UserAuthInfoDomainService {

    override fun findByEmail(email: Email): UserAuthInfo? {
        return userAuthInfoRepository.findByEmail(email)
    }

    override fun getByUserId(userId: UUID): UserAuthInfo {
        return userAuthInfoRepository.getByUserId(userId)
    }

    override fun deleteById(id: UUID) {
        userAuthInfoRepository.deleteById(id)
    }

    override fun create(
        user: User,
        socialLoginProvider: SocialLoginProvider
    ): UserAuthInfo {
        return UserAuthInfo.create(
            user = user,
            socialLoginProvider = socialLoginProvider,
        ).also { userAuthInfoRepository.save(it) }
    }

}
