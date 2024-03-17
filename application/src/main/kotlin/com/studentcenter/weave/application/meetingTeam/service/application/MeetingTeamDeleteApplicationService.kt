package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.meeting.port.inbound.CancelAllMeetingUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamDeleteUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MeetingTeamDeleteApplicationService(
    private val cancelAllMeetingUseCase: CancelAllMeetingUseCase,
    private val meetingTeamDomainService: MeetingTeamDomainService,
) : MeetingTeamDeleteUseCase {

    @Transactional
    override fun invoke(command: MeetingTeamDeleteUseCase.Command) {
        meetingTeamDomainService.getById(command.id).let {
            if (it.status == MeetingTeamStatus.PUBLISHED) {
                cancelAllMeetingUseCase.invoke(CancelAllMeetingUseCase.Command(it.id))
            }
            meetingTeamDomainService.deleteById(it.id)
        }
    }

}
