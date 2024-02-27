package com.studentcenter.weave.application.meetingTeam.port.inbound

import java.util.*

interface MeetingTeamEnterUseCase {

    fun invoke(invitationCode: UUID)

}
