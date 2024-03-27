package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import java.util.*

class QueryUserStub : QueryUser {

    override fun getById(id: UUID): User {
        return UserFixtureFactory.create(id = id)
    }

    override fun isUserUniversityVerified(userId: UUID): Boolean {
        return true
    }

}
