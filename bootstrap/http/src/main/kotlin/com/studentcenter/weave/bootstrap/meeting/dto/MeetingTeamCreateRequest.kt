package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.domain.meeting.enums.Location

data class MeetingTeamCreateRequest(
    val teamIntroduce: String,
    val memberCount: Int,
    val location: Location
)
