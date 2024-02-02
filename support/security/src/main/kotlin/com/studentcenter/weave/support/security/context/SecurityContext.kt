package com.studentcenter.weave.support.security.context

import com.studentcenter.weave.support.security.authority.Authentication

interface SecurityContext {

    fun getAuthentication(): Authentication?

    fun setAuthentication(authentication: Authentication)

}
