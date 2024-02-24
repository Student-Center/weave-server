package com.studentcenter.weave.application.meeting.service.domain

import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.enums.TeamType
import java.util.*

interface MeetingDomainService {

    fun findAllPendingMeetingByTeamId(
        teamId: UUID,
        teamType: TeamType,
        next: UUID?,
        limit: Int,
    ): List<Meeting>

}
