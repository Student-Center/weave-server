package com.studentcenter.weave.application.meeting.port.inbound

import java.util.*

fun interface CancelAllMeetingUseCase {

    fun invoke(command: Command)

    data class Command(val teamId: UUID)
}
