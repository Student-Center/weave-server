package com.studentcenter.weave.application.service.domain.impl

import com.studentcenter.weave.application.port.outbound.UserAuthInfoRepository
import com.studentcenter.weave.application.service.domain.UserAuthInfoDomainService
import com.studentcenter.weave.domain.entity.UserAuthInfo
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Service

@Service
class UserAuthInfoDomainServiceImpl(
    private val userAuthInfoRepository: UserAuthInfoRepository
): UserAuthInfoDomainService {

    override fun findByEmail(email: Email): UserAuthInfo? {
        return userAuthInfoRepository.findByEmail(email)
    }

}
