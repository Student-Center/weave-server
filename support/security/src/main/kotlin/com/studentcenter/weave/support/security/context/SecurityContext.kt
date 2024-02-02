package com.studentcenter.weave.support.security.context

import com.studentcenter.weave.support.security.authority.Authentication

interface SecurityContext<T: Authentication> {

    fun getAuthentication(): T

    fun setAuthentication(authentication: T)

}
