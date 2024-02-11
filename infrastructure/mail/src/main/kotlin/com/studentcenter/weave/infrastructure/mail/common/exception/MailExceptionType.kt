package com.studentcenter.weave.infrastructure.mail.common.exception

import com.studentcenter.weave.support.common.exception.CustomExceptionType

enum class MailExceptionType(override val code: String) : CustomExceptionType {
    MAIL_CLIENT_EXCEPTION("EMAIL-001"),
}
