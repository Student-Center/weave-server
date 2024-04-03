package com.studentcenter.weave.application.chat.service

import com.studentcenter.weave.application.chat.port.inbound.GetChatRoom
import com.studentcenter.weave.application.chat.port.outbound.ChatRoomRepository
import com.studentcenter.weave.application.chat.vo.ChatRoomDetail
import com.studentcenter.weave.application.chat.vo.ChatRoomDetail.Companion.toDetail
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.application.user.port.inbound.GetUser
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetChatRoomService(
    private val getUser: GetUser,
    private val getMeetingTeam: GetMeetingTeam,
    private val chatRoomRepository: ChatRoomRepository,
): GetChatRoom{

    override fun getDetailById(id: UUID): ChatRoomDetail {
        val chatRoom = chatRoomRepository.getById(id)

        return chatRoom.toDetail(
            getMeetingTeam = getMeetingTeam,
            getUser = getUser
        )
    }
}
