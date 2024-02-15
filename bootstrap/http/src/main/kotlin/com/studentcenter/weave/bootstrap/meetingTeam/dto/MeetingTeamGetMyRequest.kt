package com.studentcenter.weave.bootstrap.meetingTeam.dto

import com.studentcenter.weave.support.common.dto.ScrollRequest
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "내 미팅 팀 목록 조회 요청")
data class MeetingTeamGetMyRequest(
    @Schema(description = "이전 요청의 마지막 iteam id, 최초 요청시 null")
    override val next: UUID?,
    @Schema(description = "조회할 개수")
    override val limit: Int
) : ScrollRequest<UUID?>(
    next = next,
    limit = limit
)
