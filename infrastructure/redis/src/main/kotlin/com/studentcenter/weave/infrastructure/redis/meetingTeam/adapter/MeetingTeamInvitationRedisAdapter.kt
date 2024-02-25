package com.studentcenter.weave.infrastructure.redis.meetingTeam.adapter

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamInvitationRepository
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import com.studentcenter.weave.infrastructure.redis.meetingTeam.entity.MeetingTeamInvitationRedisHash
import com.studentcenter.weave.infrastructure.redis.meetingTeam.repository.MeetingTeamInvitationRedisRepository
import com.studentcenter.weave.support.common.vo.Url
import org.springframework.stereotype.Component
import kotlin.time.Duration.Companion.seconds

@Component
class MeetingTeamInvitationRedisAdapter(
    private val meetingTeamInvitationRedisRepository: MeetingTeamInvitationRedisRepository,
) : MeetingTeamInvitationRepository {

    override fun save(meetingTeamInvitation: MeetingTeamInvitation) {
        val meetingTeamInvitationRedisHash = MeetingTeamInvitationRedisHash(
            invitationLink = meetingTeamInvitation.invitationLink,
            teamId = meetingTeamInvitation.teamId,
            expirationDuration = meetingTeamInvitation.expirationDuration.inWholeSeconds,
        )
        meetingTeamInvitationRedisRepository.save(meetingTeamInvitationRedisHash)
    }

    override fun getByInvitationLink(invitationLink: Url): MeetingTeamInvitation? {
        return meetingTeamInvitationRedisRepository
            .findById(invitationLink.toString())
            .map {
                MeetingTeamInvitation(
                    teamId = it.teamId,
                    invitationLink = it.invitationLink,
                    expirationDuration = it.expirationDuration.seconds
                )
            }.orElse(null)
    }

}
