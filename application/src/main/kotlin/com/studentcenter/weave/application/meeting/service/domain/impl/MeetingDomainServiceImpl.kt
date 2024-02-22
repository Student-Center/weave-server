package com.studentcenter.weave.application.meeting.service.domain.impl

import com.studentcenter.weave.application.meeting.port.outbound.MeetingRepository
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.domain.meeting.entity.Meeting
import org.springframework.stereotype.Service
import java.util.*

@Service
class MeetingDomainServiceImpl(
    private val meetingRepository: MeetingRepository,
) : MeetingDomainService {

    override fun scrollPendingMeetingByUserId(
        userId: UUID,
        isRequester: Boolean,
        next: UUID?,
        limit: Int,
    ): List<Meeting> {
        return meetingRepository.scrollPendingMeetingByUserId(
            userId = userId,
            isRequester = isRequester,
            next = next,
            limit = limit,
        )
    }

}
