package com.studentcenter.weave.application.meeting.port.inbound

import com.studentcenter.weave.application.meeting.vo.PendingMeetingInfo
import com.studentcenter.weave.support.common.dto.ScrollResponse
import java.util.*

interface ScrollPendingMeetingUseCase {

    fun invoke(command: Command) :Result

    data class Command(
        val isRequester: Boolean,
        val next: UUID?,
        val limit: Int,
    )

    data class Result(
        override val items: List<PendingMeetingInfo>,
        override val next: UUID?
    ) : ScrollResponse<PendingMeetingInfo, UUID?>(
        items = items,
        next = next,
        total = items.size,
    )

}