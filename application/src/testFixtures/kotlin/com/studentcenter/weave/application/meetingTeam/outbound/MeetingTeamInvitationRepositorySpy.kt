package com.studentcenter.weave.application.meetingTeam.outbound

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamInvitationRepository
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import com.studentcenter.weave.support.common.vo.Url
import java.util.concurrent.ConcurrentHashMap

class MeetingTeamInvitationRepositorySpy : MeetingTeamInvitationRepository {

    private val bucket = ConcurrentHashMap<Url, MeetingTeamInvitation>()

    override fun save(meetingTeamInvitation: MeetingTeamInvitation) {
        bucket[meetingTeamInvitation.invitationLink] = meetingTeamInvitation
    }

    override fun getByInvitationLink(invitationLink: Url): MeetingTeamInvitation {
        return bucket[invitationLink] ?: throw NoSuchElementException()
    }

    fun clear() {
        bucket.clear()
    }

}
