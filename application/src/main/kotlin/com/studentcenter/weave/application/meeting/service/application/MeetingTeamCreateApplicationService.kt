package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamCreateUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.user.vo.UserAuthentication
import org.springframework.stereotype.Service

@Service
class MeetingTeamCreateApplicationService (
    private val meetingTeamDomainService: MeetingTeamDomainService,
): MeetingTeamCreateUseCase {

    override fun invoke(command: MeetingTeamCreateUseCase.Command) {
        val userAuthentication: UserAuthentication = getCurrentUserAuthentication()
        meetingTeamDomainService.create(
            teamIntroduce = command.teamIntroduce,
            memberCount = command.memberCount,
            leaderUserId = userAuthentication.userId,
            location = command.location
        )
    }

}
