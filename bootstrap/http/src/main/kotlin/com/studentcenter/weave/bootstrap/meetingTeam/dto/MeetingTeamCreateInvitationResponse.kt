package com.studentcenter.weave.bootstrap.meetingTeam.dto

import com.studentcenter.weave.support.common.vo.Url
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "미팅 팀 초대 링크 생성 응답")
data class MeetingTeamCreateInvitationResponse(
    @Schema(description = "초대 링크")
    val meetingTeamInvitationLink: Url,
)
