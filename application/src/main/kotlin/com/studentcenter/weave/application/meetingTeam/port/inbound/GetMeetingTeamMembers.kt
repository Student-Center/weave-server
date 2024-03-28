package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import java.util.*

fun interface GetMeetingTeamMembers {

    fun findAllByTeamIds(teamIds: List<UUID>): List<MeetingMember>

}
