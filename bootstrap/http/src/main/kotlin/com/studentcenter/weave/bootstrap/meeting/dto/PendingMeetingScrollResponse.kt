package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.application.meeting.vo.PendingMeetingInfo
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import com.studentcenter.weave.domain.meeting.enums.TeamType
import com.studentcenter.weave.support.common.dto.ScrollResponse
import java.time.LocalDateTime
import java.util.*

data class PendingMeetingScrollResponse(
    override val items: List<MeetingDto>,
    override val next: UUID?,
    override val total: Int,
) : ScrollResponse<PendingMeetingScrollResponse.MeetingDto, UUID?>(
    items = items,
    next = next,
    total = items.size,
) {

    data class MeetingDto(
        val id: UUID,
        val requestingTeam: MeetingTeamDto,
        val receivingTeam: MeetingTeamDto,
        val teamType: TeamType,
        val status: MeetingStatus,
        val createdAt: LocalDateTime,
        val pendingEndAt: LocalDateTime,
    ) {
        companion object {
            fun from(meetingInfo: PendingMeetingInfo) : MeetingDto {
                return MeetingDto(
                    id = meetingInfo.id,
                    requestingTeam = MeetingTeamDto.from(meetingInfo.requestingTeam),
                    receivingTeam = MeetingTeamDto.from(meetingInfo.receivingTeam),
                    teamType = meetingInfo.teamType,
                    status = meetingInfo.status,
                    createdAt = meetingInfo.createdAt,
                    pendingEndAt = meetingInfo.pendingEndAt,
                )
            }
        }
    }

    data class MeetingTeamDto(
        val id: UUID,
        val teamIntroduce: String,
        val memberCount: Int,
        val gender: String,
        val memberInfos: List<MeetingMemberDto>,
    ) {
        companion object {
            fun from(info: MeetingTeamInfo): MeetingTeamDto {
                return MeetingTeamDto(
                    id = info.team.id,
                    teamIntroduce = info.team.teamIntroduce.value,
                    memberCount = info.team.memberCount,
                    gender = info.team.gender.name,
                    memberInfos = info.memberInfos.map {
                        MeetingMemberDto(
                            id = it.id,
                            userId = it.user.id,
                            universityName = it.university.displayName,
                            mbti = it.user.mbti.value,
                            birthYear = it.user.birthYear.value,
                            animalType = it.user.animalType?.name,
                        )
                    }
                )
            }
        }

        data class MeetingMemberDto(
            val id: UUID,
            val userId: UUID,
            val universityName: String,
            val mbti: String,
            val birthYear: Int,
            val animalType: String?,
        )

    }
}

