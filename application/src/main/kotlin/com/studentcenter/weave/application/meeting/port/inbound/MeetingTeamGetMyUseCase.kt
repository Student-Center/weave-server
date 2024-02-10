package com.studentcenter.weave.application.meeting.port.inbound

import com.studentcenter.weave.application.meeting.vo.MeetingTeamInfo
import com.studentcenter.weave.support.common.dto.ScrollRequest
import com.studentcenter.weave.support.common.dto.ScrollResponse
import java.util.UUID

interface MeetingTeamGetMyUseCase {

    fun invoke(scrollRequest: ScrollRequest): Result

    data class Result(
        override val item: List<MeetingTeamInfo>,
        override val lastItemId: UUID?,
        override val limit: Int,
    ) : ScrollResponse<MeetingTeamInfo>(
        item = item,
        lastItemId = lastItemId,
        limit = limit,
    )

}
