package com.studentcenter.weave.application.suggestion.port.inbound

import com.studentcenter.weave.application.user.vo.UserAuthentication

interface CreateSuggestion {

    fun invoke(command: Command)

    data class Command(
        val userAuthentication: UserAuthentication,
        val contents: String,
    )
}
