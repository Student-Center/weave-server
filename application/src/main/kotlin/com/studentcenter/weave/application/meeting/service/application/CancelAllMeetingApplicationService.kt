package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.meeting.port.inbound.CancelAllMeetingUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CancelAllMeetingApplicationService(
    private val meetingDomainService: MeetingDomainService,
) : CancelAllMeetingUseCase {

    @Transactional
    override fun invoke(command: CancelAllMeetingUseCase.Command) {
        meetingDomainService.cancelAllNotFinishedMeetingByTeamId(command.teamId)
    }
}
