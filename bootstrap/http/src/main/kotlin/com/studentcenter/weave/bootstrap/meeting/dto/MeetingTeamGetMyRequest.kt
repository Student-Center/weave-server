package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.support.common.dto.ScrollRequest
import java.util.*

data class MeetingTeamGetMyRequest(
    override val next: UUID?,
    override val limit: Int
) : ScrollRequest<UUID?>(
    next = next,
    limit = limit
)
