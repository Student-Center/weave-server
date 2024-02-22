package com.studentcenter.weave.bootstrap.meeting.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Request meeting request")
data class MeetingRequestRequest(
    @Schema(description = "미팅 요청 상대팀")
    val receivingMeetingTeamId: UUID,
)
