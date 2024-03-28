package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.application.user.vo.UserDetail
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import java.util.*

class GetUserStub : GetUser {

    override fun getById(id: UUID): User {
        return UserFixtureFactory.create(id = id)
    }

    override fun getUserDetail(id: UUID): UserDetail {
        TODO("Not yet implemented")
    }

}
