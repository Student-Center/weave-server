package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import java.util.*

interface MeetingTeamGetByInvitationCodeUseCase {

    fun invoke(invitationCode: UUID): MeetingTeam

}
