package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.CancelAllMeetingUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamLeaveUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MeetingTeamLeaveApplicationService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val cancelAllMeetingUseCase: CancelAllMeetingUseCase,
) : MeetingTeamLeaveUseCase {

    @Transactional
    override fun invoke(command: MeetingTeamLeaveUseCase.Command) {
        meetingTeamDomainService.getById(command.meetingTeamId).let {
            meetingTeamDomainService.deleteMember(getCurrentUserAuthentication().userId, it.id)
            if (it.isPublished()) {
                cancelAllMeetingUseCase.invoke(CancelAllMeetingUseCase.Command(it.id))
                meetingTeamDomainService.deleteById(it.id)
            }
        }
    }

}
