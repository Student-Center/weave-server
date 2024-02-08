package com.studentcenter.weave.application.meeting.service.domain

import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.entity.User

interface MeetingTeamDomainService {

    fun create(
        teamIntroduce: TeamIntroduce,
        memberCount: Int,
        leaderUser: User,
        location: Location,
    ): MeetingTeam

}
