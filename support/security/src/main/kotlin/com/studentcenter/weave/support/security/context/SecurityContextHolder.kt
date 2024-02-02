package com.studentcenter.weave.support.security.context

object SecurityContextHolder {

    private val contextHolder = ThreadLocal<SecurityContext>()

    fun getContext(): SecurityContext {
        return contextHolder.get()
    }

    fun setContext(context: SecurityContext) {
        contextHolder.set(context)
    }

    fun clearContext() {
        contextHolder.remove()
    }

}
