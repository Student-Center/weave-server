package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.meeting.port.inbound.GetMeetingAttendances
import com.studentcenter.weave.application.meeting.service.domain.MeetingAttendanceDomainService
import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetMeetingAttendancesService(
    private val meetingAttendanceDomainService: MeetingAttendanceDomainService,
) : GetMeetingAttendances {

    override fun invoke(meetingId: UUID): List<MeetingAttendance> {
        return meetingAttendanceDomainService.findAllByMeetingId(meetingId)
    }

}
