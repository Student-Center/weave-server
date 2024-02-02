package com.studentcenter.weave.application.common.security.context

import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.support.security.context.SecurityContext

class UserSecurityContext(
    private var authentication: UserAuthentication,
) : SecurityContext<UserAuthentication> {

    override fun getAuthentication(): UserAuthentication {
        return authentication
    }

    override fun setAuthentication(authentication: UserAuthentication) {
        this.authentication = authentication
    }

}
