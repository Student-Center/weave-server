package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import java.util.*

class GetUserStub : GetUser {

    override fun getById(id: UUID): User {
        return UserFixtureFactory.create(id = id)
    }

    override fun findAllByIds(ids: List<UUID>): List<User> {
        return ids.map { UserFixtureFactory.create(id = it) }
    }

}
