package com.studentcenter.weave.application.meeting.port.outbound

import com.studentcenter.weave.domain.meeting.entity.Meeting

interface MeetingRepository {

    fun save(meeting: Meeting)

}
