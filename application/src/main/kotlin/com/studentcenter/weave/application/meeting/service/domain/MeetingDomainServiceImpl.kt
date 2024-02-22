package com.studentcenter.weave.application.meeting.service.domain

import com.studentcenter.weave.application.meeting.port.outbound.MeetingRepository
import com.studentcenter.weave.application.meeting.service.domain.impl.MeetingDomainService
import com.studentcenter.weave.domain.meeting.entity.Meeting
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MeetingDomainServiceImpl(
    private val meetingRepository: MeetingRepository,
) : MeetingDomainService {

    @Transactional
    override fun save(meeting: Meeting) {
        meetingRepository.save(meeting)
    }

}
