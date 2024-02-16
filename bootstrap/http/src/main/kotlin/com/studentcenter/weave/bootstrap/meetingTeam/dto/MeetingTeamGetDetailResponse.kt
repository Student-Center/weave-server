package com.studentcenter.weave.bootstrap.meetingTeam.dto

import com.studentcenter.weave.application.meetingTeam.vo.MeetingMemberDetailInfo
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.domain.user.enums.Gender
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "미팅 팀 상세 조회 응답")
data class MeetingTeamGetDetailResponse(
    val id: UUID,
    val teamIntroduce: String,
    val memberCount: Int,
    val location: Location,
    val gender: Gender,
    val members: List<MeetingMemberDto>,
    val status: MeetingTeamStatus,
) {

    data class MeetingMemberDto(
        val userId: UUID,
        val universityName: String,
        val majorName: String,
        val mbti: String,
        val birthYear: Int,
        val role: MeetingMemberRole,
        val animalType: AnimalType?,
        val height: Int?,
    ) {
        companion object {
            fun from(meetingMemberDetailInfo: MeetingMemberDetailInfo) : MeetingMemberDto {
                return MeetingMemberDto(
                    userId = meetingMemberDetailInfo.userId,
                    universityName = meetingMemberDetailInfo.universityName.value,
                    majorName = meetingMemberDetailInfo.majorName.value,
                    mbti = meetingMemberDetailInfo.mbti.value,
                    birthYear = meetingMemberDetailInfo.birthYear.value,
                    role = meetingMemberDetailInfo.role,
                    animalType = meetingMemberDetailInfo.animalType,
                    height = meetingMemberDetailInfo.height?.value,
                )
            }
        }
    }

    companion object {
        fun of(
            team: MeetingTeam,
            members: List<MeetingMemberDetailInfo>
        ): MeetingTeamGetDetailResponse {
            return MeetingTeamGetDetailResponse(
                id = team.id,
                teamIntroduce = team.teamIntroduce.value,
                memberCount = team.memberCount,
                location = team.location,
                gender = team.gender,
                members = members.map { MeetingMemberDto.from(it) },
                status = team.status,
            )
        }
    }

}
