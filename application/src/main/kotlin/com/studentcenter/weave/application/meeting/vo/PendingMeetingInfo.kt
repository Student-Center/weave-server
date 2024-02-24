package com.studentcenter.weave.application.meeting.vo

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import com.studentcenter.weave.domain.meeting.enums.TeamType
import java.time.LocalDateTime
import java.util.*

data class PendingMeetingInfo(
    val id: UUID,
    val requestingTeam: MeetingTeamInfo,
    val receivingTeam: MeetingTeamInfo,
    val teamType: TeamType,
    val status: MeetingStatus,
    val createdAt: LocalDateTime,
    val pendingEndAt: LocalDateTime,
)
