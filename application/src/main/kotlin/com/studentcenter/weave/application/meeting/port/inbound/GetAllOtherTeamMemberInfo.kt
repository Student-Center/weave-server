package com.studentcenter.weave.application.meeting.port.inbound

import com.studentcenter.weave.application.meetingTeam.vo.MemberInfo
import java.util.*

fun interface GetAllOtherTeamMemberInfo {

    fun invoke(meetingId: UUID): List<MemberInfo>

}
