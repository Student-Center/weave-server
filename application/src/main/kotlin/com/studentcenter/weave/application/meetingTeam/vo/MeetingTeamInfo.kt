package com.studentcenter.weave.application.meetingTeam.vo

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam

data class MeetingTeamInfo(
    val team: MeetingTeam,
    val memberInfos: List<MemberInfo>
)
