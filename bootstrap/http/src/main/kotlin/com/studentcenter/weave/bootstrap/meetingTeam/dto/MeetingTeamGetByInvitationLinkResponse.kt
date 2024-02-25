package com.studentcenter.weave.bootstrap.meetingTeam.dto

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "초대 링크를 통해 미팅 팀 조회")
data class MeetingTeamGetByInvitationLinkResponse(
    val teamId: UUID,
    val teamIntroduce: String,
    val status: MeetingTeamStatus,
) {

    companion object {

        fun of(meetingTeam: MeetingTeam) = MeetingTeamGetByInvitationLinkResponse(
            teamId = meetingTeam.id,
            teamIntroduce = meetingTeam.teamIntroduce.value,
            status = meetingTeam.status,
        )

    }

}
