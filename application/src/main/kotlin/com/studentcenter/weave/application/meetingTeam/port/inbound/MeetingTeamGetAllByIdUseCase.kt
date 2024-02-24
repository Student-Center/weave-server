package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import java.util.*

fun interface MeetingTeamGetAllByIdUseCase {

    fun invoke(ids: List<UUID>): Result


    data class Result(
        val teamInfos: List<MeetingTeamInfo>,
    )

}
