package com.studentcenter.weave.application.common.security.context

import com.studentcenter.weave.application.common.exception.AuthExceptionType
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.security.context.SecurityContextHolder

fun getCurrentUserAuthentication(): UserAuthentication {
    val userAuthentication: UserAuthentication? = SecurityContextHolder
        .getContext<UserAuthentication>()
        ?.getAuthentication()

    return userAuthentication ?: run {
        val message = "인증되지 않은 사용자입니다."
        throw CustomException(
            type = AuthExceptionType.USER_NOT_AUTHENTICATED,
            message = message
        )
    }
}
