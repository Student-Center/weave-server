package com.studentcenter.weave.application.meetingTeam.outbound

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamInvitationRepository
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MeetingTeamInvitationRepositorySpy : MeetingTeamInvitationRepository {

    private val bucket = ConcurrentHashMap<UUID, MeetingTeamInvitation>()

    override fun save(meetingTeamInvitation: MeetingTeamInvitation) {
        bucket[meetingTeamInvitation.invitationCode] = meetingTeamInvitation
    }

    override fun findByInvitationCode(invitationCode: UUID): MeetingTeamInvitation {
        return bucket[invitationCode] ?: throw NoSuchElementException()
    }

    fun clear() {
        bucket.clear()
    }

}
