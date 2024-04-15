package com.studentcenter.weave.bootstrap.meeting.controller

import com.studentcenter.weave.application.chat.port.inbound.CreateChatRoom
import com.studentcenter.weave.domain.meeting.event.MeetingCompletedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Controller

@Controller
class MeetingEventHandler(
    private val createChatRoom: CreateChatRoom,
) {

    @EventListener
    fun handleMeetingEvent(meetingCompletedEvent: MeetingCompletedEvent) {
        meetingCompletedEvent
            .entity
            .also { createChatRoom.invoke(it) }
    }

}
