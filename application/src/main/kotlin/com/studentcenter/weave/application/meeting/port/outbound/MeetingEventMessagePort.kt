package com.studentcenter.weave.application.meeting.port.outbound

import com.studentcenter.weave.application.meeting.vo.MeetingMatchingEvent

fun interface MeetingEventMessagePort {

    fun sendMeetingIsMatchedMessage(meetingMatchingEvent: MeetingMatchingEvent)

}
