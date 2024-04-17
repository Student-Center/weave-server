package com.studentcenter.weave.application.chat.port.inbound

import com.studentcenter.weave.domain.meeting.event.MeetingCompletedEvent

interface CreateChatRoom {

    fun invoke(meetingCompletedEvent: MeetingCompletedEvent)

}
