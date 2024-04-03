package com.studentcenter.weave.application.chat.port.inbound

import com.studentcenter.weave.application.chat.vo.ChatRoomDetail
import java.util.*

interface GetChatRoom {

    fun getDetailById(id: UUID): ChatRoomDetail

}
