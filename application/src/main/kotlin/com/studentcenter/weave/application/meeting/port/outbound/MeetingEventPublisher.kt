package com.studentcenter.weave.application.meeting.port.outbound

import com.studentcenter.weave.domain.meeting.event.MeetingCompletedEvent

interface MeetingEventPublisher {

    fun publish(meetingCompletedEvent: MeetingCompletedEvent)

}
