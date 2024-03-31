package com.studentcenter.weave.application.meetingTeam.outbound

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingMemberRepository
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MeetingMemberRepositorySpy : MeetingMemberRepository {

    private val bucket = ConcurrentHashMap<UUID, MeetingMember>()

    override fun save(meetingMember: MeetingMember) {
        bucket[meetingMember.id] = meetingMember
    }

    override fun countByMeetingTeamId(meetingTeamId: UUID): Int {
        TODO()
    }

    override fun findByMeetingTeamIdAndUserId(
        meetingTeamId: UUID,
        userId: UUID,
    ): MeetingMember? {
        TODO()
    }

    override fun findByUserId(userId: UUID): MeetingMember? {
        return bucket.values.find { it.userId == userId }
    }

    override fun findAllByMeetingTeamId(meetingTeamId: UUID): List<MeetingMember> {
        TODO()
    }

    override fun deleteAllByMeetingTeamId(meetingTeamId: UUID) {
        TODO()
    }

    override fun deleteById(id: UUID) {
        bucket.remove(id)
    }

    fun getByMeetingTeamIdAndUserId(
        meetingTeamId: UUID,
        userId: UUID,
    ): MeetingMember {
        return findByMeetingTeamIdAndUserId(meetingTeamId, userId)
            ?: throw NoSuchElementException("MeetingMember not found")
    }

    fun clear() {
        bucket.clear()
    }

}
