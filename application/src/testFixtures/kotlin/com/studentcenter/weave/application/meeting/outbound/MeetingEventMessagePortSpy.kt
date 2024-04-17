package com.studentcenter.weave.application.meeting.outbound

import com.studentcenter.weave.application.meeting.port.outbound.MeetingEventMessagePort
import com.studentcenter.weave.application.meeting.vo.MeetingMatchingEvent

class MeetingEventMessagePortSpy: MeetingEventMessagePort {

    private val meetingMatchingEvents = mutableListOf<MeetingMatchingEvent>()

    override fun sendMeetingIsMatchedMessage(meetingMatchingEvent: MeetingMatchingEvent) {
        meetingMatchingEvents.add(meetingMatchingEvent)
    }

    fun count(): Int {
        return meetingMatchingEvents.size
    }

    fun clear() {
        meetingMatchingEvents.clear()
    }

}
