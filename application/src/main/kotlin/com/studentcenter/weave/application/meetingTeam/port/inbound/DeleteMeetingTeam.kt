package com.studentcenter.weave.application.meetingTeam.port.inbound

import java.util.*

fun interface DeleteMeetingTeam {

    fun invoke(id: UUID)

}
