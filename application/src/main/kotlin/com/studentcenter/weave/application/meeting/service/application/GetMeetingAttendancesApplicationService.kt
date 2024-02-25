package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.meeting.port.inbound.GetMeetingAttendancesUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingAttendanceDomainService
import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetMeetingAttendancesApplicationService(
    private val meetingAttendanceDomainService: MeetingAttendanceDomainService,
) : GetMeetingAttendancesUseCase {

    override fun invoke(meetingId: UUID): List<MeetingAttendance> {
        return meetingAttendanceDomainService.findAllByMeetingId(meetingId)
    }

}
