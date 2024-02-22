package com.studentcenter.weave.application.meeting.service.domain

import com.studentcenter.weave.application.meeting.port.outbound.MeetingAttendanceRepository
import com.studentcenter.weave.application.meeting.port.outbound.MeetingRepository
import com.studentcenter.weave.application.meeting.service.domain.impl.MeetingDomainService
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MeetingDomainServiceImpl(
    private val meetingRepository: MeetingRepository,
    private val meetingAttendanceRepository: MeetingAttendanceRepository,
) : MeetingDomainService {

    @Transactional
    override fun create(
        requestingMeetingTeamId: UUID,
        receivingMeetingTeamId: UUID,
        meetingMemberIds: List<UUID>
    ) {
        require(requestingMeetingTeamId != receivingMeetingTeamId) { "미팅 요청팀과 받는팀이 같으면 안돼요!" }

        val meeting: Meeting = Meeting.create(
            requestingMeetingTeamId,
            receivingMeetingTeamId,
        ).also {
            meetingRepository.save(it)
        }

        meetingMemberIds
            .map { MeetingAttendance.create(meeting.id, it) }
            .toList()
            .also { meetingAttendanceRepository.saveAll(it) }
    }

}
