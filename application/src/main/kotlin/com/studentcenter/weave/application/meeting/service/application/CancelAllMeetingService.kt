package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.meeting.port.inbound.CancelAllMeeting
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CancelAllMeetingService(
    private val meetingDomainService: MeetingDomainService,
) : CancelAllMeeting {

    @Transactional
    override fun invoke(command: CancelAllMeeting.Command) {
        meetingDomainService.cancelAllNotFinishedMeetingByTeamId(command.teamId)
    }
}
