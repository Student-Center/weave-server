package com.studentcenter.weave.application.meeting.port.outbound

import com.studentcenter.weave.application.meeting.vo.MeetingMatchingEvent

fun interface MeetingEventPort {

    fun sendMeetingIsMatchedMessage(meetingMatchingEvent: MeetingMatchingEvent)

}
