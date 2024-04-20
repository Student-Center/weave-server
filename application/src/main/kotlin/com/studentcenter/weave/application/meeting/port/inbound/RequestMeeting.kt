package com.studentcenter.weave.application.meeting.port.inbound

import java.util.*

fun interface RequestMeeting {

    fun invoke(command: Command)

    data class Command(
        val receivingMeetingTeamId: UUID,
    )

}
