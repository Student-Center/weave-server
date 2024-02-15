package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import java.util.*

interface MeetingTeamEditUseCase {

    fun invoke(command: Command)

    data class Command(
        val id: UUID,
        val location: Location?,
        val memberCount: Int?,
        val teamIntroduce: TeamIntroduce?,
    )

}
