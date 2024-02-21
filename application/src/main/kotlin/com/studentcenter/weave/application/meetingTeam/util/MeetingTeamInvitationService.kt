package com.studentcenter.weave.application.meetingTeam.util

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import java.util.*

interface MeetingTeamInvitationService {

    fun create(teamId: UUID): MeetingTeamInvitation

}
