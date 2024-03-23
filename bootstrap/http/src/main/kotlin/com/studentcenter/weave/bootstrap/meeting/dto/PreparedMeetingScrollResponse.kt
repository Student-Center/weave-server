package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.application.meeting.vo.PreparedMeetingInfo
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import com.studentcenter.weave.support.common.dto.ScrollResponse
import java.time.LocalDateTime
import java.util.*
import kotlin.NoSuchElementException

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

    data class MeetingTeamDto(
        val id: UUID,
        val teamIntroduce: String,
        val memberCount: Int,
        val location: String,
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
                    location = info.team.location.value,
                    memberInfos = info.memberInfos.map {
                        MeetingMemberDto(
                            id = it.id,
                            userId = it.user.id,
                            universityName = it.university.displayName,
                            majorName = it.major?.name?.value ?: throw NoSuchElementException("학과 정보를 조회할 수 없습니다."),
                            mbti = it.user.mbti.value,
                            birthYear = it.user.birthYear.value,
                            animalType = it.user.animalType?.name,
                            height = it.user.height?.value,
                            isUnivVerified = it.user.isUnivVerified,
                            avatar = it.user.avatar?.value,
                        )
                    }
                )
            }
        }

        data class MeetingMemberDto(
            val id: UUID,
            val userId: UUID,
            val universityName: String,
            val majorName: String,
            val mbti: String,
            val birthYear: Int,
            val animalType: String?,
            val height: Int?,
            val isUnivVerified: Boolean,
            val avatar: String?,
        )

    }
}

