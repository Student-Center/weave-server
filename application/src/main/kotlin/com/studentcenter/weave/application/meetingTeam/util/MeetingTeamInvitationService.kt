package com.studentcenter.weave.application.meetingTeam.util

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import com.studentcenter.weave.support.common.vo.Url
import java.util.*

interface MeetingTeamInvitationService {

    fun create(teamId: UUID): MeetingTeamInvitation

    fun getByInvitationLink(invitationLink: Url): MeetingTeamInvitation?

}
