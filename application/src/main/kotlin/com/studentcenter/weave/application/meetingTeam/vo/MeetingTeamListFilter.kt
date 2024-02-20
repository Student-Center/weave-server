package com.studentcenter.weave.application.meetingTeam.vo

import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.user.enums.Gender

data class MeetingTeamListFilter(
    val memberCount: Int?,
    val youngestMemberBirthYear: Int,
    val oldestMemberBirthYear: Int,
    val preferredLocations: List<Location>?,
    val gender: Gender?,
    val status: MeetingTeamStatus,
)
