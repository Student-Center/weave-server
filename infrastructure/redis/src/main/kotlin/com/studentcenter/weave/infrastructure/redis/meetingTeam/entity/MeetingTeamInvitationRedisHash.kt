package com.studentcenter.weave.infrastructure.redis.meetingTeam.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.*

@RedisHash("meeting_team_invitation")
class MeetingTeamInvitationRedisHash(
    teamId: UUID,
    invitationCode: UUID,
    invitationLink: String,
    expirationDuration: Long,
) {

    @Id
    var invitationCode: UUID = invitationCode
        private set

    var teamId: UUID = teamId
        private set

    var invitationLink: String = invitationLink
        private set

    @TimeToLive
    var expirationDuration: Long = expirationDuration
        private set

}
