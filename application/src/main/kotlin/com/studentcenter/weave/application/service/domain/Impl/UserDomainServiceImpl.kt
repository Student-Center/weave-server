package com.studentcenter.weave.application.service.domain.Impl

import com.studentcenter.weave.application.service.domain.UserDomainService
import com.studentcenter.weave.domain.entity.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDomainServiceImpl : UserDomainService {

    override fun getById(id: UUID): User {
        TODO("Not yet implemented")
    }
}
