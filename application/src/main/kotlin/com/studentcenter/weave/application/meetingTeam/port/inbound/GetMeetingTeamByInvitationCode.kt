package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import java.util.*

fun interface GetMeetingTeamByInvitationCode {

    fun invoke(invitationCode: UUID): MeetingTeam

}
