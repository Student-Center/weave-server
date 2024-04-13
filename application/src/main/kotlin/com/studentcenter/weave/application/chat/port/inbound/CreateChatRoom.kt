package com.studentcenter.weave.application.chat.port.inbound

import com.studentcenter.weave.domain.meeting.entity.Meeting

interface CreateChatRoom {

    fun invoke(meeting: Meeting)

}
