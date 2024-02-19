package com.studentcenter.weave.bootstrap.meetingTeam.dto

import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetListUseCase
import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.support.common.dto.ScrollRequest
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "미팅 팀 목록 조회 요청")
data class MeetingTeamGetListRequest(
    @Schema(description = "찾는 미팅 팀의 멤버 수")
    val memberCount: Int?,

    @Schema(description = "미팅 상대팀 멤버의 최소 년생")
    val youngestMemberBirthYear: Int?,

    @Schema(description = "미팅 상대팀 멤버의 최대 년생")
    val oldestMemberBirthYear: Int?,

    @Schema(description = "선호하는 미팅 지역. 여러 지역 선택 가능, 쉼표(,)로 구분. 미선택시 전체 지역을 대상으로 조회")
    val preferredLocations: List<Location>?,

    @Schema(description = "이전 요청의 마지막 item id, 최초 요청시 null")
    override val next: UUID?,

    @Schema(description = "조회할 아이템 개수", required = true)
    override val limit: Int
) : ScrollRequest<UUID?>(
    next = next,
    limit = limit
) {

    init {
        require(youngestMemberBirthYear == null || oldestMemberBirthYear != null) {
            "최소 년생이 있을 경우 최대 년생도 필수로 입력해야 해요!"
        }
    }

    fun toCommand(): MeetingTeamGetListUseCase.Command {
        return MeetingTeamGetListUseCase.Command(
            memberCount = memberCount,
            youngestMemberBirthYear = youngestMemberBirthYear,
            oldestMemberBirthYear = oldestMemberBirthYear,
            preferredLocations = preferredLocations,
            next = next,
            limit = limit
        )
    }

}
