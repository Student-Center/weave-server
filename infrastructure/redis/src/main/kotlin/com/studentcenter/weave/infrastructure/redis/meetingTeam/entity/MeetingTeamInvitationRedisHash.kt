package com.studentcenter.weave.infrastructure.redis.meetingTeam.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import kotlin.time.Duration
import java.util.UUID

@RedisHash("meeting_team_invitation")
class MeetingTeamInvitationRedisHash(
    teamId: UUID,
    invitationLink: String,
    expirationDuration: Duration,
) {

    @Id
    var invitationLink: String = invitationLink
        private set

    var teamId: UUID = teamId
        private set

    @TimeToLive
    var expirationDuration: Duration = expirationDuration
        private set

}
