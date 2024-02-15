package com.studentcenter.weave.bootstrap.meetingTeam.dto

import com.studentcenter.weave.domain.meetingTeam.enums.Location
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Edit meeting team request")
data class MeetingTeamEditRequest(
    val location: Location?,
    val memberCount: Int?,
    val teamIntroduce: String?,
)
