package com.studentcenter.weave.application.meeting.port.inbound

import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce

interface MeetingTeamCreateUseCase {

    fun invoke(command: Command)

    data class Command(
        val teamIntroduce: TeamIntroduce,
        val memberCount: Int,
        val location: Location
    )

}
