package com.studentcenter.weave.application.meeting.outbound

import com.studentcenter.weave.application.meeting.port.outbound.MeetingAttendanceRepository
import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MeetingAttendanceRepositorySpy : MeetingAttendanceRepository {

    private val bucket = ConcurrentHashMap<UUID, MeetingAttendance>()

    override fun save(meetingAttendance: MeetingAttendance) {
        bucket[meetingAttendance.id] = meetingAttendance
    }

    override fun existsByMeetingIdAndMeetingMemberId(
        meetingId: UUID,
        meetingMemberId: UUID,
    ): Boolean {
        return bucket.values.any {
            it.meetingId == meetingId && it.meetingMemberId == meetingMemberId
        }
    }

    override fun findAllByMeetingId(meetingId: UUID): List<MeetingAttendance> {
        return bucket.values.filter { it.meetingId == meetingId }
    }

    override fun countByMeetingIdAndAttend(meetingId: UUID): Int {
        return bucket.values.count { it.meetingId == meetingId && it.isAttend }
    }

    fun clear() {
        bucket.clear()
    }


}
