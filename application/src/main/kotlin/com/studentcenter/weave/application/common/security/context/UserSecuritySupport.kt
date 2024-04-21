package com.studentcenter.weave.application.common.security.context

import com.studentcenter.weave.application.common.exception.AuthException
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.support.security.context.SecurityContextHolder

fun getCurrentUserAuthentication(): UserAuthentication {
    val userAuthentication: UserAuthentication? = SecurityContextHolder
        .getContext<UserAuthentication>()
        ?.getAuthentication()

    return userAuthentication ?: run {
        throw AuthException.UserNotAuthenticated()
    }
}
