package com.studentcenter.weave.application.service.domain

import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.domain.entity.UserFixtureFactory
import java.util.*

class UserDomainServiceStub: UserDomainService {

    override fun getById(id: UUID): User {
        return UserFixtureFactory.create()
    }

}
