package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce

interface MeetingTeamCreateUseCase {

    fun invoke(command: Command)

    data class Command(
        val teamIntroduce: TeamIntroduce,
        val memberCount: Int,
        val location: Location
    )

}
