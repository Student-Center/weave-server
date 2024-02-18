package com.studentcenter.weave.infrastructure.redis.meetingTeam.adapter

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamInvitationRepository
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import com.studentcenter.weave.infrastructure.redis.meetingTeam.entity.MeetingTeamInvitationRedisHash
import com.studentcenter.weave.infrastructure.redis.meetingTeam.repository.MeetingTeamInvitationRedisRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class MeetingTeamInvitationRedisAdapter(
    private val meetingTeamInvitationRedisRepository: MeetingTeamInvitationRedisRepository,
) : MeetingTeamInvitationRepository {

    override fun save(
        meetingTeamInvitation: MeetingTeamInvitation
    ): UUID {
        val meetingTeamInvitationRedisHash = MeetingTeamInvitationRedisHash(
            code = meetingTeamInvitation.invitationCode,
            teamId = meetingTeamInvitation.teamId,
            expirationDuration = meetingTeamInvitation.expirationDuration
        )

        val savedMeetingTeamInvitationRedisHash =
            meetingTeamInvitationRedisRepository.save(meetingTeamInvitationRedisHash)

        return savedMeetingTeamInvitationRedisHash.code
    }

}
