package com.studentcenter.weave.application.meeting.service.domain.impl

import java.util.*

interface MeetingDomainService {

    fun create(
        requestingMeetingTeamId: UUID,
        receivingMeetingTeamId: UUID,
        meetingMemberIds: List<UUID>
    )

}
