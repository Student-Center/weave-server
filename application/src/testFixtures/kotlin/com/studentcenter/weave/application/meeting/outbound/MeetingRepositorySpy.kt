package com.studentcenter.weave.application.meeting.outbound

import com.studentcenter.weave.application.meeting.port.outbound.MeetingRepository
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.enums.TeamType
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MeetingRepositorySpy : MeetingRepository {

    private val bucket = ConcurrentHashMap<UUID, Meeting>()

    override fun save(meeting: Meeting) {
        bucket[meeting.id] = meeting
    }

    override fun findAllPendingMeetingByTeamId(
        teamId: UUID,
        teamType: TeamType,
        next: UUID?,
        limit: Int,
    ): List<Meeting> {
        return if (teamType == TeamType.REQUESTING) {
            bucket
                .values
                .filter { it.requestingTeamId == teamId && (next == null || it.id < next) }
                .take(limit)
        } else {
            bucket
                .values
                .filter { it.receivingTeamId == teamId && (next == null || it.id < next) }
                .take(limit)
        }
    }

    fun findByRequestingMeetingTeamIdAndReceivingMeetingTeamId(
        requestingTeamId: UUID,
        receivingTeamId: UUID,
    ): Meeting? {
        return bucket.values.find {
            it.requestingTeamId == requestingTeamId && it.receivingTeamId == receivingTeamId
        }
    }

    fun clear() {
        bucket.clear()
    }

}
