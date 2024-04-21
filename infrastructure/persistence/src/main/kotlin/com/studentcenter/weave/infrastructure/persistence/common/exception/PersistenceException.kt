package com.studentcenter.weave.infrastructure.persistence.common.exception

import com.studentcenter.weave.support.common.exception.CustomException

sealed class PersistenceException(
    codeNumber: Int,
    message: String,
) : CustomException(CODE_PREFIX, codeNumber, message) {

    class ResourceNotFound(message: String = "") :
        PersistenceException(codeNumber = 1, message = message)

    companion object {
        const val CODE_PREFIX = "PERSISTENCE"
    }

}
