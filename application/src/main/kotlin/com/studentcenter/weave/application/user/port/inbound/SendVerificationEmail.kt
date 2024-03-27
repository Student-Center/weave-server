package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.support.common.vo.Email

fun interface SendVerificationEmail {

    fun invoke(universityEmail: Email)

}
