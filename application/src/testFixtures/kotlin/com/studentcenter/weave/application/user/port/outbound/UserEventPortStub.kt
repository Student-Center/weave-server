package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.User

open class UserEventPortStub : UserEventPort {

    override fun sendRegistrationMessage(user: User, userCount: Int) {
        TODO("Not yet implemented")
    }

}
