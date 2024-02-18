package com.studentcenter.weave.application.meetingTeam.port.inbound

import java.util.UUID

fun interface MeetingTeamCreateInvitationUseCase {

    fun invoke(meetingTeamId: UUID): Result

    data class Result(
        val teamId: UUID,
        val invitationCode: UUID,
    )

}
