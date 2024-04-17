package com.studentcenter.weave.bootstrap.meeting.controller

import com.studentcenter.weave.application.chat.port.inbound.CreateChatRoom
import com.studentcenter.weave.application.meeting.port.inbound.NotifyMeetingEvent
import com.studentcenter.weave.domain.meeting.event.MeetingCompletedEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Controller

@Controller
class MeetingEventHandler(
    private val createChatRoom: CreateChatRoom,
    private val notifyMeetingEvent: NotifyMeetingEvent,
) {

    @EventListener
    fun handleMeetingEvent(meetingCompletedEvent: MeetingCompletedEvent) {
        CoroutineScope(Dispatchers.Default).launch {
            launch { createChatRoom.invoke(meetingCompletedEvent) }
            launch { notifyMeetingEvent.notifyMeetingCompleted(meetingCompletedEvent) }
        }
    }

}
