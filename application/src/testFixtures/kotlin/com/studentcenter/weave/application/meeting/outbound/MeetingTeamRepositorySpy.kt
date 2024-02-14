package com.studentcenter.weave.application.meeting.outbound

import com.studentcenter.weave.application.meeting.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MeetingTeamRepositorySpy : MeetingTeamRepository {

    private val bucket = ConcurrentHashMap<UUID, MeetingTeam>()

    override fun save(meetingTeam: MeetingTeam) {
        bucket[meetingTeam.id] = meetingTeam
    }

    override fun getById(id: UUID): MeetingTeam {
        return bucket[id] ?: throw NoSuchElementException()
    }

    override fun findAllByMemberUserId(
        userId: UUID,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam> {
        return bucket.values.toList()
    }

    fun getLast(): MeetingTeam {
        return bucket.values.last()
    }

    fun clear() {
        bucket.clear()
    }
}
