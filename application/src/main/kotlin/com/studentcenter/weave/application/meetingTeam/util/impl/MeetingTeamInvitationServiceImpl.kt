package com.studentcenter.weave.application.meetingTeam.util.impl

import com.studentcenter.weave.application.common.properties.MeetingTeamInvitationProperties
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamInvitationRepository
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import com.studentcenter.weave.support.common.uuid.UuidCreator
import org.springframework.stereotype.Component
import java.util.*

@Component
class MeetingTeamInvitationServiceImpl(
    private val meetingTeamInvitationProperties: MeetingTeamInvitationProperties,
    private val meetingTeamInvitationRepository: MeetingTeamInvitationRepository,
) : MeetingTeamInvitationService {

    override fun create(teamId: UUID): String {
        val invitationLink = generateInvitationLink()

        meetingTeamInvitationRepository.save(
            MeetingTeamInvitation.of(
                teamId = teamId,
                invitationLink = invitationLink,
                expirationDuration = meetingTeamInvitationProperties.expireSeconds,
            )
        )

        return invitationLink
    }

    private fun generateInvitationLink(): String {
        val invitationCode = UuidCreator.create()

        return meetingTeamInvitationProperties.urlPrefix + invitationCode
    }

}
