package com.studentcenter.weave.application.meeting.port.inbound

import java.util.*

fun interface CancelAllMeeting {

    fun invoke(command: Command)

    data class Command(val teamId: UUID)
}
