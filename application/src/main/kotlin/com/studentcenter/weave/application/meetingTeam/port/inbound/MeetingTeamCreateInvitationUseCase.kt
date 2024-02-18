package com.studentcenter.weave.application.meetingTeam.port.inbound

import java.util.UUID

fun interface MeetingTeamCreateInvitationUseCase {

    fun invoke(command: Command): Result

    data class Command(
        val meetingTeamId: UUID,
    )

    data class Result(
        val teamId: UUID,
        val invitationCode: UUID,
    )

}
