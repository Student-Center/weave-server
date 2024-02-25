package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import java.util.*

fun interface MeetingTeamInfoGetAllByIdUseCase {

    fun invoke(ids: List<UUID>): List<MeetingTeamInfo>

}
