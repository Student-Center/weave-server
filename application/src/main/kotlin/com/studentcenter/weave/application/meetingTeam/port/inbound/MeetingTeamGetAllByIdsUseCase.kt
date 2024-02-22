package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import java.util.*

fun interface MeetingTeamGetAllByIdsUseCase {

    fun invoke(command: Command): Result

    data class Command(
        val ids: List<UUID>,
    )

    data class Result(
        val teamInfos: List<MeetingTeamInfo>,
    )

}
