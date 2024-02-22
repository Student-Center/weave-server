package com.studentcenter.weave.application.meetingTeam.vo

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.user.entity.User
import java.util.*

data class MeetingTeamInfo(
    val team: MeetingTeam,
    val memberInfos: List<MemberInfo>
) {

    data class MemberInfo(
        val id: UUID,
        val user: User,
        val university: University,
        val role: MeetingMemberRole,
    )

}
