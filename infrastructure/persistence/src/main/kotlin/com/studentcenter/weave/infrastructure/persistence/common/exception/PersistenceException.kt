package com.studentcenter.weave.infrastructure.persistence.common.exception

import com.studentcenter.weave.support.common.exception.CustomExceptionType

enum class PersistenceExceptionType(override val code: String): CustomExceptionType {
    RESOURCE_NOT_FOUND("PERSISTENCE-001"),
}
