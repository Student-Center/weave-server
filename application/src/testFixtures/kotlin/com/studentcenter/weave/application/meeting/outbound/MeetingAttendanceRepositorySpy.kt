package com.studentcenter.weave.application.meeting.outbound

import com.studentcenter.weave.application.meeting.port.outbound.MeetingAttendanceRepository
import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MeetingAttendanceRepositorySpy : MeetingAttendanceRepository {

    private val bucket = ConcurrentHashMap<UUID, MeetingAttendance>()

    fun save(meetingAttendance: MeetingAttendance) {
        bucket[meetingAttendance.id] = meetingAttendance
    }

    override fun findAllByMeetingId(meetingId: UUID): List<MeetingAttendance> {
        return bucket.values.filter { it.meetingId == meetingId }
    }

    fun clear() {
        bucket.clear()
    }


}
