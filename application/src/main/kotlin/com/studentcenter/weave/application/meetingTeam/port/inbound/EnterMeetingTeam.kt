package com.studentcenter.weave.application.meetingTeam.port.inbound

import java.util.*

fun interface EnterMeetingTeam {

    fun invoke(invitationCode: UUID)

}
