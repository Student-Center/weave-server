package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.application.meetingTeam.vo.MyMeetingTeamInfo
import com.studentcenter.weave.support.common.dto.ScrollResponse
import java.util.*

interface MeetingTeamGetMyUseCase {

    fun invoke(command: Command): Result

    data class Command(
        val next: UUID?,
        val limit: Int,
    )

    data class Result(
        override val items: List<MyMeetingTeamInfo>,
        override val next: UUID?,
    ) : ScrollResponse<MyMeetingTeamInfo, UUID?>(
        items = items,
        next = next,
        total = items.size,
    )

}
