package com.studentcenter.weave.application.meeting.port.outbound

import com.studentcenter.weave.domain.meeting.entity.Meeting
import java.util.*

interface MeetingRepository {

    fun save(meeting: Meeting)

    fun scrollPendingMeetingByUserId(
        userId: UUID,
        isRequester: Boolean,
        next: UUID?,
        limit: Int,
    ): List<Meeting>

}
