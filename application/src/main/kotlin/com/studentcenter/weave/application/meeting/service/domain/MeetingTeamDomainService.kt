package com.studentcenter.weave.application.meeting.service.domain

import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import java.util.*

interface MeetingTeamDomainService {

    fun create(
        teamIntroduce: TeamIntroduce,
        memberCount: Int,
        leaderUserId: UUID,
        location: Location
    ): MeetingTeam

}
