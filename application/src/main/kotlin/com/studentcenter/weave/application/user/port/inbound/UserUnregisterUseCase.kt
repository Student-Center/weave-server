package com.studentcenter.weave.application.user.port.inbound

interface UserUnregisterUseCase {

    fun invoke(command: Command)

    data class Command(
        val reason: String? = null,
    )

}
