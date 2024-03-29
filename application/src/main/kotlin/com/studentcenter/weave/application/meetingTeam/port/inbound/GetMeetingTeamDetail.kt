package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.application.meetingTeam.vo.MeetingMemberDetail
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.user.vo.MbtiAffinityScore
import java.util.*

fun interface GetMeetingTeamDetail {

    fun invoke(command: Command): Result

    data class Command(
        val meetingId: UUID
    )

    data class Result(
        val meetingTeam: MeetingTeam,
        val members: List<MeetingMemberDetail>,
        val affinityScore: MbtiAffinityScore? = null
    )

}
