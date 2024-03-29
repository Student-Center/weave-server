package com.studentcenter.weave.application.meetingTeam.port.inbound

import java.util.*

fun interface JoinMeetingTeam {

    fun invoke(invitationCode: UUID)

}
