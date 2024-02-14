package com.studentcenter.weave.application.meeting.outbound

import com.studentcenter.weave.application.meeting.port.outbound.MeetingMemberRepository
import com.studentcenter.weave.domain.meeting.entity.MeetingMember
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MeetingMemberRepositorySpy : MeetingMemberRepository {

    private val bucket = ConcurrentHashMap<UUID, MeetingMember>()

    override fun save(meetingMember: MeetingMember) {
        bucket[meetingMember.id] = meetingMember
    }

    override fun countByMeetingTeamId(meetingTeamId: UUID): Int {
        return bucket.values.count { it.meetingTeamId == meetingTeamId }
    }

    override fun findByMeetingTeamIdAndUserId(
        meetingTeamId: UUID,
        userId: UUID
    ): MeetingMember? {
        return bucket.values.find { it.meetingTeamId == meetingTeamId && it.userId == userId }
    }

    override fun findAllByMeetingTeamId(meetingTeamId: UUID): List<MeetingMember> {
        return bucket.values.filter { it.meetingTeamId == meetingTeamId }
    }

    override fun deleteAllByMeetingTeamId(id: UUID) {
        bucket.values.removeIf { it.meetingTeamId == id }
    }

    fun getByMeetingTeamIdAndUserId(
        meetingTeamId: UUID,
        userId: UUID
    ): MeetingMember {
        return findByMeetingTeamIdAndUserId(meetingTeamId, userId)
            ?: throw NoSuchElementException("MeetingMember not found")
    }

    fun clear() {
        bucket.clear()
    }

}
