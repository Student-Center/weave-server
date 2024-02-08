package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamCreateUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MeetingTeamCreateApplicationService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val userDomainService: UserDomainService,
) : MeetingTeamCreateUseCase {

    @Transactional
    override fun invoke(command: MeetingTeamCreateUseCase.Command) {
        val userAuthentication: UserAuthentication = getCurrentUserAuthentication()
        val user: User = userDomainService.getById(userAuthentication.userId)
        meetingTeamDomainService.create(
            teamIntroduce = command.teamIntroduce,
            memberCount = command.memberCount,
            leaderUser = user,
            location = command.location,
        )
    }

}
