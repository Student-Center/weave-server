package com.studentcenter.weave.application.meeting.port.inbound

import com.studentcenter.weave.application.meeting.vo.PreparedMeetingInfo
import com.studentcenter.weave.support.common.dto.ScrollRequest
import com.studentcenter.weave.support.common.dto.ScrollResponse
import java.util.*

fun interface ScrollPreparedMeeting {

    fun invoke(command: Command): Result

    data class Command(
        override val next: UUID?,
        override val limit: Int,
    ) : ScrollRequest<UUID?>(next, limit)

    data class Result(
        override val items: List<PreparedMeetingInfo>,
        override val next: UUID?,
    ) : ScrollResponse<PreparedMeetingInfo, UUID?>(
        items = items,
        next = next,
        total = items.size,
    )

}
