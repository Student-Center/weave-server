package com.studentcenter.weave.application.meetingTeam.util.impl

import com.studentcenter.weave.application.common.properties.MeetingTeamInvitationProperties
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamInvitationRepository
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Url
import org.springframework.stereotype.Component
import java.util.*
import kotlin.time.Duration.Companion.seconds

@Component
class MeetingTeamInvitationServiceImpl(
    private val meetingTeamInvitationProperties: MeetingTeamInvitationProperties,
    private val meetingTeamInvitationRepository: MeetingTeamInvitationRepository,
) : MeetingTeamInvitationService {

    override fun create(teamId: UUID): MeetingTeamInvitation {
        val invitationCode = generateInvitationCode()
        val invitationLink = generateInvitationLink(invitationCode)

        return MeetingTeamInvitation(
            teamId = teamId,
            invitationCode = invitationCode,
            invitationLink = invitationLink,
            expirationDuration = meetingTeamInvitationProperties.expireSeconds.seconds,
        ).apply {
            meetingTeamInvitationRepository.save(this)
        }
    }

    override fun findByInvitationCode(invitationCode: UUID): MeetingTeamInvitation? {
        return meetingTeamInvitationRepository.findByInvitationCode(invitationCode)
    }

    private fun generateInvitationCode(): UUID {
        return UuidCreator.create()
    }

    private fun generateInvitationLink(invitationCode: UUID): Url {
        return Url("${meetingTeamInvitationProperties.urlPrefix}$invitationCode")
    }

}
