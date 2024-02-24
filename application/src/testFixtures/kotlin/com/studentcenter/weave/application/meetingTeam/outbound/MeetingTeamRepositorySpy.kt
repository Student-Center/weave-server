package com.studentcenter.weave.application.meetingTeam.outbound

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamListFilter
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MeetingTeamRepositorySpy : MeetingTeamRepository {

    private val bucket = ConcurrentHashMap<UUID, MeetingTeam>()
    private val memberUserIdToTeamIdMap = ConcurrentHashMap<UUID, UUID>()

    override fun save(meetingTeam: MeetingTeam) {
        bucket[meetingTeam.id] = meetingTeam
    }

    override fun getById(id: UUID): MeetingTeam {
        return bucket[id] ?: throw NoSuchElementException()
    }

    override fun getByIdAndStatus(
        id: UUID,
        status: MeetingTeamStatus
    ): MeetingTeam {
        return bucket.values.first { it.id == id && it.status == status }
    }

    override fun getByMemberUserId(userId: UUID): MeetingTeam {
        val meetingTeamId: UUID = memberUserIdToTeamIdMap[userId] ?: throw NoSuchElementException()
        return bucket[meetingTeamId] ?: throw NoSuchElementException()
    }

    override fun findByMemberUserId(userId: UUID): MeetingTeam? {
        val meetingTeamId: UUID = memberUserIdToTeamIdMap[userId] ?: return null
        return bucket[meetingTeamId]
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

    override fun scrollByFilter(
        filter: MeetingTeamListFilter,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam> {
        return bucket.values.toList()
    }

    override fun findAllById(ids: List<UUID>): List<MeetingTeam> {
        return bucket.values.filter { it.id in ids }.toList()
    }

    fun putUserToTeamMember(userId: UUID, teamId: UUID) {
        memberUserIdToTeamIdMap[userId] = teamId
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
