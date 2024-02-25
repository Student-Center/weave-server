package com.studentcenter.weave.application.meeting.service.domain.impl

import com.studentcenter.weave.application.meeting.port.outbound.MeetingRepository
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.enums.TeamType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MeetingDomainServiceImpl(
    private val meetingRepository: MeetingRepository,
) : MeetingDomainService {

    @Transactional
    override fun save(meeting: Meeting) {
        meetingRepository.save(meeting)
    }

    override fun findAllPendingMeetingByTeamId(
        teamId: UUID,
        teamType: TeamType,
        next: UUID?,
        limit: Int,
    ): List<Meeting> {
        return meetingRepository.findAllPendingMeetingByTeamId(
            teamId = teamId,
            teamType = teamType,
            next = next,
            limit = limit,
        )
    }

    override fun getById(id: UUID): Meeting {
        return meetingRepository.getById(id)
    }

}
