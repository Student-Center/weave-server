package com.studentcenter.weave.application.meetingTeam.vo

import java.util.*
import kotlin.time.Duration

data class MeetingTeamInvitation(
    val teamId: UUID,
    val invitationLink: String,
    val expirationDuration: Duration,
) {

    companion object {

        fun of(
            teamId: UUID,
            invitationLink: String,
            expirationDuration: Duration,
        ): MeetingTeamInvitation {
            return MeetingTeamInvitation(
                teamId = teamId,
                invitationLink = invitationLink,
                expirationDuration = expirationDuration
            )
        }

    }

}
