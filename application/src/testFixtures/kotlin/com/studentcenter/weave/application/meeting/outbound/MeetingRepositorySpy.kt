package com.studentcenter.weave.application.meeting.outbound

import com.studentcenter.weave.application.meeting.port.outbound.MeetingRepository
import com.studentcenter.weave.domain.meeting.entity.Meeting
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MeetingRepositorySpy : MeetingRepository {

    private val bucket = ConcurrentHashMap<UUID, Meeting>()

    override fun save(meeting: Meeting) {
        bucket[meeting.id] = meeting
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
