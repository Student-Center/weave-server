package com.studentcenter.weave.application.meeting.port.inbound

import java.util.*

interface MeetingTeamDeleteUseCase {

    fun invoke(command: Command)

    data class Command(
        val id: UUID,
    )

}
