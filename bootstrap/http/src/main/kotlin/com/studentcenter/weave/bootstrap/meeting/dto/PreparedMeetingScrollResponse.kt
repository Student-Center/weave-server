package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.application.meeting.vo.PreparedMeetingInfo
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import com.studentcenter.weave.support.common.dto.ScrollResponse
import java.time.LocalDateTime
import java.util.*

data class PreparedMeetingScrollResponse(
    override val items: List<MeetingDto>,
    override val next: UUID?,
    override val total: Int,
) : ScrollResponse<PreparedMeetingScrollResponse.MeetingDto, UUID?>(
    items = items,
    next = next,
    total = items.size,
) {

    data class MeetingDto(
        val id: UUID,
        val memberCount: Int,
        val otherTeam: MeetingTeamDto,
        val status: MeetingStatus,
        val createdAt: LocalDateTime,
    ) {
        companion object {
            fun from(meetingInfo: PreparedMeetingInfo) : MeetingDto {
                return MeetingDto(
                    id = meetingInfo.id,
                    memberCount = meetingInfo.memberCount,
                    otherTeam = MeetingTeamDto.from(meetingInfo.otherTeam),
                    status = meetingInfo.status,
                    createdAt = meetingInfo.createdAt,
                )
            }
        }
    }
}

