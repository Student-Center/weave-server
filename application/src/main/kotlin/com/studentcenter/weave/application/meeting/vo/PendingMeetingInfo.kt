package com.studentcenter.weave.application.meeting.vo

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import java.time.LocalDateTime
import java.util.*

data class PendingMeetingInfo(
    val id: UUID,
    val requesterTeam: MeetingTeamInfo,
    val receivingTeam: MeetingTeamInfo,
    val isRequestingTeam: Boolean,
    val status: MeetingStatus,
    val createdAt: LocalDateTime,
    val pendingEndAt: LocalDateTime,
)
