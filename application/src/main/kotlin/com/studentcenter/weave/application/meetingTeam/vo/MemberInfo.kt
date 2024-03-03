package com.studentcenter.weave.application.meetingTeam.vo

import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.university.entity.Major
import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.user.entity.User
import java.util.*

data class MemberInfo(
    val id: UUID,
    val user: User,
    val university: University,
    val role: MeetingMemberRole,
    val major: Major? = null,
)
