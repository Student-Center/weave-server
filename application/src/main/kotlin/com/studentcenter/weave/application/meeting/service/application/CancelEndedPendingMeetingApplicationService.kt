package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.meeting.port.inbound.CancelEndedPendingMeetingUseCase
import com.studentcenter.weave.application.meeting.port.outbound.MeetingRepository
import org.springframework.stereotype.Service

@Service
class CancelEndedPendingMeetingApplicationService(
    private val meetingRepository: MeetingRepository,
) : CancelEndedPendingMeetingUseCase {

    override fun invoke() {
        meetingRepository.cancelEndedPendingMeeting()
    }
}
