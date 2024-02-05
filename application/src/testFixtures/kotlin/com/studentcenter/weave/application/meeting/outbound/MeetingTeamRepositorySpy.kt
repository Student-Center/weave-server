package com.studentcenter.weave.application.meeting.outbound

import com.studentcenter.weave.application.meeting.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class MeetingTeamRepositorySpy: MeetingTeamRepository {

    private val bucket = ConcurrentHashMap<UUID, MeetingTeam>()

    override fun save(meetingTeam: MeetingTeam) {
        bucket[meetingTeam.id] = meetingTeam
    }

    fun findByLeaderUserId(leaderUserId: UUID): MeetingTeam? {
        return bucket.values.find { it.leaderUserId == leaderUserId }
    }

    fun clear() {
        bucket.clear()
    }
}
