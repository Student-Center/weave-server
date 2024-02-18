package com.studentcenter.weave.infrastructure.redis.meetingTeam.adapter

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamInvitationRepository
import com.studentcenter.weave.infrastructure.redis.meetingTeam.entity.MeetingTeamInvitationRedisHash
import com.studentcenter.weave.infrastructure.redis.meetingTeam.repository.MeetingTeamInvitationRedisRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class MeetingTeamInvitationRedisAdapter(
    private val meetingTeamInvitationRedisRepository: MeetingTeamInvitationRedisRepository,
) : MeetingTeamInvitationRepository {

    override fun save(
        teamId: UUID,
        invitationCode: UUID,
        expirationSeconds: Long
    ): UUID {

        val meetingTeamInvitationRedisHash = MeetingTeamInvitationRedisHash(
            code = invitationCode,
            teamId = teamId,
            expirationSeconds = expirationSeconds
        )

        val savedMeetingTeamInvitationRedisHash =
            meetingTeamInvitationRedisRepository.save(meetingTeamInvitationRedisHash)

        return savedMeetingTeamInvitationRedisHash.code

    }

}
