package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import com.studentcenter.weave.support.common.dto.ScrollResponse
import java.util.*

interface MeetingTeamGetMyUseCase {

    fun invoke(command: Command): Result

    data class Command(
        val next: UUID?,
        val limit: Int,
    )

    data class Result(
        override val item: List<MeetingTeamInfo>,
        override val next: UUID?,
    ) : ScrollResponse<MeetingTeamInfo, UUID?>(
        item = item,
        next = next,
        total = item.size,
    )

}
