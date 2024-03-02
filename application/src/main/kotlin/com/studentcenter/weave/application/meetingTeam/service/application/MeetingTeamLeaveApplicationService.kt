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
        val team = meetingTeamDomainService.getById(command.meetingTeamId)
        if (team.isPublished().not()) {
            meetingTeamDomainService.deleteMember(getCurrentUserAuthentication().userId, command.meetingTeamId)
            return
        }

        // 팀이 공개된 상태라면
        // 1. 연결된 모든 미팅 상태 취소 처리
        // 2. 팀 삭제
        cancelAllMeetingUseCase.invoke(CancelAllMeetingUseCase.Command(team.id))
        meetingTeamDomainService.deleteById(team.id)
    }

}
