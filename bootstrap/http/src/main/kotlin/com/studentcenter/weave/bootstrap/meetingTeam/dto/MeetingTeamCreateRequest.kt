package com.studentcenter.weave.bootstrap.meetingTeam.dto

import com.studentcenter.weave.domain.meetingTeam.enums.Location

data class MeetingTeamCreateRequest(
    val teamIntroduce: String,
    val memberCount: Int,
    val location: Location
)
