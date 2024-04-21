package com.studentcenter.weave.infrastructure.mail.common.exception

import com.studentcenter.weave.support.common.exception.CustomException

sealed class MailException(
    codeNumber: Int,
    message: String,
) : CustomException(CODE_PREFIX, codeNumber, message) {

    class ClientException(message: String = "") :
        MailException(codeNumber = 1, message = message)

    companion object {
        const val CODE_PREFIX = "MAIL"
    }

}
