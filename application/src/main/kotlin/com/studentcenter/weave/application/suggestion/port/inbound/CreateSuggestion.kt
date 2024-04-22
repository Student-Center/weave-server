package com.studentcenter.weave.application.suggestion.port.inbound

import java.util.*

interface CreateSuggestion {

    fun invoke(command: Command)

    data class Command(
        val userId: UUID,
        val contents: String,
    )
}
