package com.studentcenter.weave.application.meetingTeam.vo

import java.util.*

data class MeetingTeamInvitation(
    val teamId: UUID,
    val invitationCode: UUID,
    val expirationDuration: Long,
) {

    companion object {

        fun of(
            teamId: UUID,
            invitationCode: UUID,
            expirationDuration: Long,
        ): MeetingTeamInvitation {
            return MeetingTeamInvitation(
                teamId = teamId,
                invitationCode = invitationCode,
                expirationDuration = expirationDuration
            )
        }

    }

}
