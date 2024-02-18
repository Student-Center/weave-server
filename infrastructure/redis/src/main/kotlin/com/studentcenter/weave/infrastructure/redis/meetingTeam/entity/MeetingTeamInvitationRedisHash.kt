package com.studentcenter.weave.infrastructure.redis.meetingTeam.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.UUID

@RedisHash("meeting_team_invitation")
class MeetingTeamInvitationRedisHash(
    teamId: UUID,
    code: UUID,
    expirationSeconds: Long,
) {

    @Id
    var code: UUID = code
        private set

    var teamId: UUID = teamId
        private set

    @TimeToLive
    var expirationSeconds: Long = expirationSeconds
        private set

}
