package com.studentcenter.weave.bootstrap.meeting.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "create attendance request")
data class MeetingAttendanceCreateRequest(
    @Schema(description = "참여 여부")
    val attendance: Boolean,
)
