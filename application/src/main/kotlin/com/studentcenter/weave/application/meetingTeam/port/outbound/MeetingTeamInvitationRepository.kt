package com.studentcenter.weave.application.meetingTeam.port.outbound

import java.util.*

interface MeetingTeamInvitationRepository {

    fun save(
        teamId: UUID,
        invitationCode: UUID,
        expirationDuration: Long,
    ): UUID

}
