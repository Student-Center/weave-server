package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamCreateUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import com.studentcenter.weave.domain.meeting.enums.MeetingMemberRole
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MeetingTeamCreateApplicationService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val userQueryUseCase: UserQueryUseCase,
) : MeetingTeamCreateUseCase {

    @Transactional
    override fun invoke(command: MeetingTeamCreateUseCase.Command) {
        val userAuthentication: UserAuthentication = getCurrentUserAuthentication()
        val user: User = userQueryUseCase.getById(userAuthentication.userId)
        val meetingTeam = MeetingTeam.create(
            teamIntroduce = command.teamIntroduce,
            memberCount = command.memberCount,
            location = command.location,
            gender = user.gender,
        )
        meetingTeamDomainService.save(meetingTeam)
        meetingTeamDomainService.addMember(
            meetingTeam = meetingTeam,
            user = user,
            role = MeetingMemberRole.LEADER,
        )
    }

}
