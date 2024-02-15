package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.domain.meeting.enums.Location
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Edit meeting team request")
data class MeetingTeamEditRequest(
    val location: Location?,
    val memberCount: Int?,
    val teamIntroduce: String?,
)
