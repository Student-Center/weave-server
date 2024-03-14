package com.studentcenter.weave.support.common.exception

enum class SystemExceptionType(override val code: String) : CustomExceptionType {
    INTERNAL_SERVER_ERROR("SYSTEM-000"),
    RUNTIME_EXCEPTION("SYSTEM-001"),
    NOT_FOUND("SYSTEM-002"),
}
