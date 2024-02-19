package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.support.common.dto.ScrollRequest
import com.studentcenter.weave.support.common.dto.ScrollResponse
import java.util.*

fun interface MeetingTeamGetListUseCase {

    fun invoke(command: Command): Result

    data class Command(
        val memberCount: Int?,
        val youngestMemberBirthYear: Int?,
        val oldestMemberBirthYear: Int?,
        val preferredLocations: List<Location>?,
        override val next: UUID?,
        override val limit: Int
    ) : ScrollRequest<UUID?>(
        next = next,
        limit = limit
    )

    data class Result(
        override val items: List<MeetingTeamInfo>,
        override val next: UUID?,
    ) : ScrollResponse<MeetingTeamInfo, UUID?>(
        items = items,
        next = next,
        total = items.size,
    )

}
