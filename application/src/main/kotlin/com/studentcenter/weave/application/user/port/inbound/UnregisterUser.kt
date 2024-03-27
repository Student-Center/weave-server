package com.studentcenter.weave.application.user.port.inbound

fun interface UnregisterUser {

    fun invoke(command: Command)

    data class Command(
        val reason: String? = null,
    )

}
