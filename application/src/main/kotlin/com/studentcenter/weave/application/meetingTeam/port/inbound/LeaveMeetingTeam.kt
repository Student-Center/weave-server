package com.studentcenter.weave.application.meetingTeam.port.inbound

import java.util.*

fun interface LeaveMeetingTeam {

    fun invoke(id: UUID)

}
