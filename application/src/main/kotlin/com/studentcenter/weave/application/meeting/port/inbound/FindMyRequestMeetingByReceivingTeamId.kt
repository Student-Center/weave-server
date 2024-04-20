package com.studentcenter.weave.application.meeting.port.inbound

import com.studentcenter.weave.domain.meeting.entity.Meeting
import java.util.*

fun interface FindMyRequestMeetingByReceivingTeamId {

    fun invoke(receivingTeamId: UUID): Meeting?

}
