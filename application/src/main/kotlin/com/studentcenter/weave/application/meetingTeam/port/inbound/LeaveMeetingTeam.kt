package com.studentcenter.weave.application.meetingTeam.port.inbound

import java.util.*

fun interface LeaveMeetingTeam {

    fun invoke(command: Command)

    data class Command(val meetingTeamId: UUID)

}
