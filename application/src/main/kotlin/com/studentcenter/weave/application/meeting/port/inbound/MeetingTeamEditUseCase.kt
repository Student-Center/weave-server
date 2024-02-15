package com.studentcenter.weave.application.meeting.port.inbound

import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
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
