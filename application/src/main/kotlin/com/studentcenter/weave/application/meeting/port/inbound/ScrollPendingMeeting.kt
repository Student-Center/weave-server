package com.studentcenter.weave.application.meeting.port.inbound

import com.studentcenter.weave.application.meeting.vo.PendingMeetingInfo
import com.studentcenter.weave.domain.meeting.enums.TeamType
import com.studentcenter.weave.support.common.dto.ScrollResponse
import java.util.*

fun interface ScrollPendingMeeting {

    fun invoke(query: Query): Result

    data class Query(
        val teamType: TeamType,
        val next: UUID?,
        val limit: Int,
    )

    data class Result(
        override val items: List<PendingMeetingInfo>,
        override val next: UUID?,
    ) : ScrollResponse<PendingMeetingInfo, UUID?>(
        items = items,
        next = next,
        total = items.size,
    )

}
