package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.exception.MeetingTeamExceptionType
import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.CancelAllMeetingUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamLeaveUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MeetingTeamLeaveApplicationService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val cancelAllMeetingUseCase: CancelAllMeetingUseCase,
) : MeetingTeamLeaveUseCase {

    @Transactional
    override fun invoke(command: MeetingTeamLeaveUseCase.Command) {
        val myTeam = meetingTeamDomainService.getById(getCurrentUserAuthentication().userId)
        if (myTeam.id != command.meetingTeamId) {
            throw CustomException(MeetingTeamExceptionType.IS_NOT_TEAM_MEMBER, "팀의 멤버가 아닙니다.")
        }

        if (myTeam.isPublished()) {
            cancelAllMeetingUseCase.invoke(CancelAllMeetingUseCase.Command(myTeam.id))
            meetingTeamDomainService.deleteById(myTeam.id)
            return
        }

        meetingTeamDomainService.deleteMember(getCurrentUserAuthentication().userId, myTeam.id)
    }

}
