package com.studentcenter.weave.application.meetingTeam.port.inbound

import java.util.*

fun interface MeetingTeamCreateInvitationUseCase {

    fun invoke(meetingTeamId: UUID): Result

    data class Result(
        val invitationLink: String,
    )

}
