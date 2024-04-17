package com.studentcenter.weave.application.meeting.port.inbound

import com.studentcenter.weave.domain.meeting.event.MeetingCompletedEvent

interface NotifyMeetingEvent {

    fun notifyMeetingCompleted(meetingCompletedEvent: MeetingCompletedEvent)

}
