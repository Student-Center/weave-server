package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamDeleteUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MeetingTeamDeleteApplicationService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
) : MeetingTeamDeleteUseCase {

    @Transactional
    override fun invoke(command: MeetingTeamDeleteUseCase.Command) {
        meetingTeamDomainService.getById(command.id)
            .let { meetingTeamDomainService.deleteById(it.id) }
    }

}
