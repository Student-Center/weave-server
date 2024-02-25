package com.studentcenter.weave.application.meeting.service.domain.impl

import com.studentcenter.weave.application.meeting.port.outbound.MeetingAttendanceRepository
import com.studentcenter.weave.application.meeting.service.domain.MeetingAttendanceDomainService
import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import org.springframework.stereotype.Service
import java.util.*

@Service
class MeetingAttendanceDomainServiceImpl(
    private val meetingAttendanceRepository: MeetingAttendanceRepository,
) : MeetingAttendanceDomainService {

    override fun findAllByMeetingId(meetingId: UUID): List<MeetingAttendance> {
        return meetingAttendanceRepository.findAllByMeetingId(meetingId)
    }

}
