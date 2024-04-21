package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.JoinMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.domain.meetingTeam.exception.MeetingTeamException
import org.springframework.stereotype.Service
import java.util.*

@Service
class JoinMeetingTeamService(
    private val meetingTeamRepository: MeetingTeamRepository,
    private val meetingTeamInvitationService: MeetingTeamInvitationService,
    private val getUser: GetUser,
) : JoinMeetingTeam {

    override fun invoke(invitationCode: UUID) {
        val currentUser = getCurrentUserAuthentication().userId
            .let { getUser.getById(it) }

        val meetingTeam = meetingTeamInvitationService
            .findByInvitationCode(invitationCode)
            ?.let { meetingTeamRepository.getById(it.teamId) }
            ?: throw MeetingTeamException.InvitationCodeNotFound()

        meetingTeam
            .joinMember(currentUser)
            .also { meetingTeamRepository.save(it) }
    }

}
