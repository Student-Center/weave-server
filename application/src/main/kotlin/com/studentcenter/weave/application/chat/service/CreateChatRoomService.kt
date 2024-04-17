package com.studentcenter.weave.application.chat.service

import com.studentcenter.weave.application.chat.port.inbound.CreateChatRoom
import com.studentcenter.weave.application.chat.port.outbound.ChatRoomRepository
import com.studentcenter.weave.domain.chat.entity.ChatRoom
import com.studentcenter.weave.domain.meeting.event.MeetingCompletedEvent
import org.springframework.stereotype.Service

@Service
class CreateChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
) : CreateChatRoom {

    override fun invoke(meetingCompletedEvent: MeetingCompletedEvent) {
        ChatRoom
            .create(meetingCompletedEvent.entity)
            .also { chatRoomRepository.save(it) }
    }

}
