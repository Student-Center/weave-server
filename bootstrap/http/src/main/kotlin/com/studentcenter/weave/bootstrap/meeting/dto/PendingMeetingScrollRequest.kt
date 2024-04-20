package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.application.meeting.port.inbound.ScrollPendingMeeting
import com.studentcenter.weave.domain.meeting.enums.TeamType
import com.studentcenter.weave.support.common.dto.ScrollRequest
import io.swagger.v3.oas.annotations.Parameter
import org.springdoc.core.annotations.ParameterObject
import java.util.*

@ParameterObject
data class PendingMeetingScrollRequest(
    @Parameter(required = true, name = "teamType", description = "조회 팀 타입")
    val teamType: TeamType,
    @Parameter(required = false, description = "이전 요청의 마지막 item id, 최초 요청시 null")
    override val next: UUID? = null,
    @Parameter(required = false, description = "조회할 개수(Default 20)")
    override val limit: Int = 20,
) : ScrollRequest<UUID?>(
    next = next,
    limit = limit,
) {

    fun toQuery(): ScrollPendingMeeting.Query {
        return ScrollPendingMeeting.Query(
            teamType = this.teamType,
            next = this.next,
            limit = this.limit,
        )
    }

}
