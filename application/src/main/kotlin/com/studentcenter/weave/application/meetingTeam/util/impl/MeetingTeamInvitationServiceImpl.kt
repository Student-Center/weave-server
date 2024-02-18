package com.studentcenter.weave.application.meetingTeam.util.impl

import com.studentcenter.weave.application.common.properties.MeetingTeamInvitationProperties
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamInvitationRepository
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import org.springframework.stereotype.Component
import java.util.*

@Component
class MeetingTeamInvitationServiceImpl(
    private val meetingTeamInvitationProperties: MeetingTeamInvitationProperties,
    private val meetingTeamInvitationRepository: MeetingTeamInvitationRepository,
) : MeetingTeamInvitationService {

    override fun create(teamId: UUID): UUID {
        val invitationCode = UUID.randomUUID()

        return meetingTeamInvitationRepository.save(
            teamId = teamId,
            invitationCode = invitationCode,
            expirationSeconds = meetingTeamInvitationProperties.expireSeconds,
        )

    }

}
