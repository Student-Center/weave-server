package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.CreateInvitationLink
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CreateInvitationLinkService(
    private val meetingTeamInvitationService: MeetingTeamInvitationService,
    private val meetingTeamRepository: MeetingTeamRepository,
) : CreateInvitationLink {

    @Transactional
    override fun invoke(meetingTeamId: UUID): CreateInvitationLink.Result {
        val currentUserId = getCurrentUserAuthentication().userId

        val meetingTeam = meetingTeamRepository
            .getById(meetingTeamId)
            .also { it.validateInvitable(triggerUserId = currentUserId) }

        val meetingTeamInvitation = meetingTeamInvitationService.create(meetingTeam.id)

        return CreateInvitationLink.Result(
            meetingTeamInvitationLink = meetingTeamInvitation.invitationLink,
            meetingTeamInvitationCode = meetingTeamInvitation.invitationCode,
        )
    }
}
