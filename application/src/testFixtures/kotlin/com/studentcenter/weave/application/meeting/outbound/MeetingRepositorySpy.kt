package com.studentcenter.weave.application.meeting.outbound

import com.studentcenter.weave.application.meeting.port.outbound.MeetingRepository
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import com.studentcenter.weave.domain.meeting.enums.TeamType
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.NoSuchElementException

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

    override fun getById(id: UUID): Meeting {
        return bucket[id] ?: throw NoSuchElementException()
    }

    override fun findByRequestingTeamIdAndReceivingTeamId(
        requestingTeamId: UUID,
        receivingTeamId: UUID,
    ): Meeting? {
        return bucket.values.find {
            it.requestingTeamId == requestingTeamId && it.receivingTeamId == receivingTeamId
        }
    }

    override fun cancelAllNotFinishedMeetingByTeamId(teamId: UUID) {
        bucket
            .values
            .filter { it.requestingTeamId == teamId || it.receivingTeamId == teamId }
            .map { it.cancel() }
    }

    override fun existsMeetingRequest(
        requestingTeamId: UUID,
        receivingMeetingTeamId: UUID,
    ): Boolean {
        return bucket
            .values
            .any {
                it.requestingTeamId == requestingTeamId && it.receivingTeamId == receivingMeetingTeamId
            }
    }

    override fun findAllPreparedMeetingByTeamId(
        teamId: UUID,
        next: UUID?,
        limit: Int,
    ): List<Meeting> {
        return bucket
            .values
            .filter {
                // FIXME(prepared): 추후에 상태가 추가되면 Completed -> Prepared
                (it.requestingTeamId == teamId || it.receivingTeamId == teamId)
                        && it.status == MeetingStatus.COMPLETED
            }.take(limit)
    }

    fun clear() {
        bucket.clear()
    }

}
