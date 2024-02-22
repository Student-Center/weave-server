package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.application.meeting.port.inbound.ScrollPendingMeetingUseCase
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

    fun toCommand(): ScrollPendingMeetingUseCase.Command {
        return ScrollPendingMeetingUseCase.Command(
            isRequester = this.teamType == TeamType.REQUESTING,
            next = this.next,
            limit = this.limit,
        )
    }

    /**
     * Boolean 타입을 Query로 받기엔 다양한 형태(true, True, TRUE, 1등)가 생각될 수 있어
     * Query에서만 TeamType을 이용하고 이를 Boolean 필드로 변환합니다.
     */
    enum class TeamType {
        REQUESTING,
        RECEIVING;
    }

}

