package com.studentcenter.weave.application.meetingTeam.port.outbound

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import java.util.*

interface MeetingTeamInvitationRepository {

    fun save(
        meetingTeamInvitation: MeetingTeamInvitation
    ): UUID

}
