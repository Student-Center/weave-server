package com.studentcenter.weave.application.meeting.vo

import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.user.vo.Mbti

data class MeetingMatchingInfo(
    val meeting: Meeting,
    val memberCount: Int,
    val matchedMeetingCount: Int,
    val requestingMeetingTeamMbti: Mbti,
    val receivingMeetingTeamMbti: Mbti,
)
