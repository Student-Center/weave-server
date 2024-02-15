package com.studentcenter.weave.application.meetingTeam.port.inbound

import java.util.*

interface MeetingTeamDeleteUseCase {

    fun invoke(command: Command)

    data class Command(
        val id: UUID,
    )

}
