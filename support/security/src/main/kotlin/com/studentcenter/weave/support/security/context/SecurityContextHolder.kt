package com.studentcenter.weave.support.security.context

import com.studentcenter.weave.support.security.authority.Authentication

object SecurityContextHolder {

    private val contextHolder = ThreadLocal<SecurityContext<*>>()

    @Suppress("UNCHECKED_CAST")
    fun <T : Authentication> getContext(): SecurityContext<T>? {
        return contextHolder.get() as SecurityContext<T>?
    }

    fun <T : Authentication> setContext(context: SecurityContext<T>) {
        contextHolder.set(context)
    }

    fun clearContext() {
        contextHolder.remove()
    }

}
