package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.support.common.vo.Url
import java.util.*

fun interface MeetingTeamCreateInvitationUseCase {

    fun invoke(meetingTeamId: UUID): Result

    data class Result(
        val meetingTeamInvitation: Url,
    )

}
