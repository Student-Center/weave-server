package com.studentcenter.weave.application.meeting.service.domain.impl

import com.studentcenter.weave.domain.meeting.entity.Meeting

interface MeetingDomainService {

    fun save(meeting: Meeting)

}
