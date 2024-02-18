package com.studentcenter.weave.application.meetingTeam.outbound

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamInvitationRepository
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MeetingTeamInvitationRepositorySpy : MeetingTeamInvitationRepository {

    private val bucket = ConcurrentHashMap<UUID, UUID>()

    override fun save(
        meetingTeamInvitation: MeetingTeamInvitation,
    ): UUID {
        bucket[meetingTeamInvitation.invitationCode] = meetingTeamInvitation.teamId

        return meetingTeamInvitation.invitationCode
    }

    fun clear() {
        bucket.clear()
    }

}
