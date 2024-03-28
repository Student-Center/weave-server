package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import java.util.*

fun interface GetAllMeetingTeamInfo {

    fun invoke(ids: List<UUID>): List<MeetingTeamInfo>

}
