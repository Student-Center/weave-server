package com.studentcenter.weave.application.meetingTeam.outbound

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamInvitationRepository
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import com.studentcenter.weave.support.common.vo.Url
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MeetingTeamInvitationRepositorySpy : MeetingTeamInvitationRepository {

    private val bucket = ConcurrentHashMap<Url, UUID>()

    override fun save(meetingTeamInvitation: MeetingTeamInvitation) {
        bucket[meetingTeamInvitation.invitationLink] = meetingTeamInvitation.teamId
    }

    fun clear() {
        bucket.clear()
    }

}
