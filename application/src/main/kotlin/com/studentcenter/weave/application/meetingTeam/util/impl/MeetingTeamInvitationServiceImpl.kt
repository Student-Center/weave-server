package com.studentcenter.weave.application.meetingTeam.util.impl

import com.studentcenter.weave.application.common.properties.MeetingTeamInvitationProperties
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamInvitationRepository
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Url
import org.springframework.stereotype.Component
import java.util.*

@Component
class MeetingTeamInvitationServiceImpl(
    private val meetingTeamInvitationProperties: MeetingTeamInvitationProperties,
    private val meetingTeamInvitationRepository: MeetingTeamInvitationRepository,
) : MeetingTeamInvitationService {

    override fun create(teamId: UUID): MeetingTeamInvitation {
        val invitationLink = generateInvitationLink()

        val meetingTeamInvitation = MeetingTeamInvitation(
            teamId = teamId,
            invitationLink = invitationLink,
            expirationDuration = meetingTeamInvitationProperties.expireSeconds,
        )

        meetingTeamInvitationRepository.save(meetingTeamInvitation)

        return meetingTeamInvitation
    }

    private fun generateInvitationLink(): Url {
        val invitationCode = UuidCreator.create()

        return Url("${meetingTeamInvitationProperties.urlPrefix}/$invitationCode")
    }

}
