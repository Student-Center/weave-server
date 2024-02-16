package com.studentcenter.weave.application.meetingTeam.outbound

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
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

    override fun scrollByMemberUserId(
        userId: UUID,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam> {
        return bucket.values.toList()
    }

    override fun deleteById(id: UUID) {
        bucket.remove(id)
    }

    fun findById(id: UUID): MeetingTeam? {
        return bucket[id]
    }

    fun getLast(): MeetingTeam {
        return bucket.values.last()
    }

    fun clear() {
        bucket.clear()
    }
}