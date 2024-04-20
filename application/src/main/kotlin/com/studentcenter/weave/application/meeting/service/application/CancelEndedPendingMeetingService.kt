package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.meeting.port.inbound.CancelEndedPendingMeeting
import com.studentcenter.weave.application.meeting.port.outbound.MeetingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CancelEndedPendingMeetingService(
    private val meetingRepository: MeetingRepository,
) : CancelEndedPendingMeeting {

    override fun invoke() {
        meetingRepository.cancelEndedPendingMeeting()
    }

}
