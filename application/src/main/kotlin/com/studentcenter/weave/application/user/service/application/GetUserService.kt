package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.university.port.inbound.GetMajor
import com.studentcenter.weave.application.university.port.inbound.GetUniversity
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.application.user.vo.UserDetail
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class GetUserService(
    private val userDomainService: UserDomainService,
    private val getUniversity: GetUniversity,
    private val getMajor: GetMajor,
) : GetUser {

    override fun getById(id: UUID): User {
        return userDomainService.getById(id)
    }

    override fun getUserDetail(id: UUID): UserDetail {
        return userDomainService
            .getById(id)
            .let { UserDetail.of(it, getUniversity, getMajor) }
    }

}
