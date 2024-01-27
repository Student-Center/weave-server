package com.studentcenter.weave.application.service.domain.Impl

import com.studentcenter.weave.application.service.domain.UserAuthInfoDomainService
import com.studentcenter.weave.domain.entity.UserAuthInfo
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Service

@Service
class UserAuthInfoDomainServiceImpl : UserAuthInfoDomainService {

    override fun findByEmail(email: Email): UserAuthInfo? {
        TODO("Not yet implemented")
    }
}
