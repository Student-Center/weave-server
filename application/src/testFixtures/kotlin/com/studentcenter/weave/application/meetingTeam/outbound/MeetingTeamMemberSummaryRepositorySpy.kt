package com.studentcenter.weave.application.meetingTeam.outbound

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamMemberSummaryRepository
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MeetingTeamMemberSummaryRepositorySpy : MeetingTeamMemberSummaryRepository {

    private val bucket = ConcurrentHashMap<UUID, MeetingTeamMemberSummary>()

    override fun save(meetingTeamMemberSummary: MeetingTeamMemberSummary) {
        bucket[meetingTeamMemberSummary.id] = meetingTeamMemberSummary
    }

    override fun deleteById(id: UUID) {
        bucket.remove(id)
    }

    override fun getById(id: UUID): MeetingTeamMemberSummary {
        return bucket[id] ?: throw NoSuchElementException()
    }

    override fun getByMeetingTeamId(meetingTeamId: UUID): MeetingTeamMemberSummary {
        return bucket.values.find { it.meetingTeamId == meetingTeamId }
            ?: throw NoSuchElementException()
    }

}
