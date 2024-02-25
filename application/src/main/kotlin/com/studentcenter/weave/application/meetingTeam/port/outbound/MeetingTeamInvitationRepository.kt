package com.studentcenter.weave.application.meetingTeam.port.outbound

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import com.studentcenter.weave.support.common.vo.Url

interface MeetingTeamInvitationRepository {

    fun save(meetingTeamInvitation: MeetingTeamInvitation)

    fun getByInvitationLink(invitationLink: Url): MeetingTeamInvitation?

}
