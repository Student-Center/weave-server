package com.studentcenter.weave.infrastructure.redis.meetingTeam.entity

import com.studentcenter.weave.support.common.vo.Url
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.*

@RedisHash("meeting_team_invitation")
class MeetingTeamInvitationRedisHash(
    teamId: UUID,
    invitationLink: Url,
    expirationDuration: Long,
) {

    @Id
    var invitationLink: Url = invitationLink
        private set

    var teamId: UUID = teamId
        private set

    @TimeToLive
    var expirationDuration: Long = expirationDuration
        private set

}
