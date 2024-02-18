package com.studentcenter.weave.application.meetingTeam.outbound

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamInvitationRepository
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MeetingTeamInvitationRepositorySpy : MeetingTeamInvitationRepository {

    private val bucket = ConcurrentHashMap<UUID, UUID>()
    override fun save(
        teamId: UUID,
        invitationCode: UUID,
        expirationSeconds: Long
    ): UUID {
        bucket[invitationCode] = teamId

        return invitationCode
    }

    fun clear() {
        bucket.clear()
    }

}
