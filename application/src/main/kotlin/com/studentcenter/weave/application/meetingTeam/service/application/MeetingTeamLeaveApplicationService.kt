package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamLeaveUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MeetingTeamLeaveApplicationService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
) : MeetingTeamLeaveUseCase {

    @Transactional
    override fun invoke(command: MeetingTeamLeaveUseCase.Command) {
        getCurrentUserAuthentication()
            .userId
            .also { meetingTeamDomainService.deleteMember(it, command.meetingTeamId) }
    }

}
