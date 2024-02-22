package com.studentcenter.weave.application.meeting.service.domain

import com.studentcenter.weave.domain.meeting.entity.Meeting
import java.util.*

interface MeetingDomainService {

    fun findAllPendingMeetingByTeamId(
        teamId: UUID,
        isRequester: Boolean,
        next: UUID?,
        limit: Int,
    ): List<Meeting>

}
