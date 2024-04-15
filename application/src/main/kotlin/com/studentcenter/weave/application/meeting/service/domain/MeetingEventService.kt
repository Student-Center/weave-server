package com.studentcenter.weave.application.meeting.service.domain

import com.studentcenter.weave.application.meeting.port.outbound.MeetingEventPublisher
import com.studentcenter.weave.domain.meeting.event.MeetingCompletedEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class MeetingEventService(
    private val eventPublisher: ApplicationEventPublisher,
): MeetingEventPublisher {

    override fun publish(meetingCompletedEvent: MeetingCompletedEvent) {
        eventPublisher.publishEvent(meetingCompletedEvent)
    }

}
