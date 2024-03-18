package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.User

interface UserEventPort {

    fun sendRegistrationMessage(user: User)

}
