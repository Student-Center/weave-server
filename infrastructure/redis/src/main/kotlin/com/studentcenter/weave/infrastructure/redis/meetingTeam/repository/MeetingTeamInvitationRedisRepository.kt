package com.studentcenter.weave.infrastructure.redis.meetingTeam.repository

import com.studentcenter.weave.infrastructure.redis.meetingTeam.entity.MeetingTeamInvitationRedisHash
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MeetingTeamInvitationRedisRepository :
    CrudRepository<MeetingTeamInvitationRedisHash, UUID>
