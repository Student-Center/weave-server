package com.studentcenter.weave.application.meeting.vo

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import java.time.LocalDateTime
import java.util.*

data class PreparedMeetingInfo(
    val id: UUID,
    val memberCount: Int,
    val otherTeam: MeetingTeamInfo,
    val status: MeetingStatus,
    val createdAt: LocalDateTime,
)
