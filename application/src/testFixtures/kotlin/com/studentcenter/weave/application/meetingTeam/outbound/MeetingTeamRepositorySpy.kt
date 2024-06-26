package com.studentcenter.weave.application.meetingTeam.outbound

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamListFilter
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
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

    override fun getByIdAndStatus(
        id: UUID,
        status: MeetingTeamStatus,
    ): MeetingTeam {
        return bucket.values.first { it.id == id && it.status == status }
    }

    override fun getByMemberUserId(userId: UUID): MeetingTeam {
        return bucket.values.first { it.members.any { member -> member.userId == userId } }
    }

    override fun findByMemberUserId(userId: UUID): MeetingTeam? {
        return bucket.values.find { it.members.any { member -> member.userId == userId } }
    }

    override fun scrollByMemberUserId(
        userId: UUID,
        next: UUID?,
        limit: Int,
    ): List<MeetingTeam> {
        return bucket.values.toList()
    }

    override fun deleteById(id: UUID) {
        bucket.remove(id)
    }

    override fun scrollByFilter(
        filter: MeetingTeamListFilter,
        next: UUID?,
        limit: Int,
    ): List<MeetingTeam> {
        return bucket.values.filter {
            (filter.memberCount == null || it.memberCount == filter.memberCount)
                    && (filter.gender == null || it.gender == filter.gender)
                    && it.status == filter.status
                    && (next == null || it.id <= next)
        }.take(limit)
    }

    override fun findAllById(ids: List<UUID>): List<MeetingTeam> {
        return bucket.values.filter { it.id in ids }.toList()
    }

    fun findById(id: UUID): MeetingTeam? {
        return bucket[id]
    }

    fun clear() {
        bucket.clear()
    }

}
