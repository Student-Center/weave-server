package com.studentcenter.weave.bootstrap.meetingTeam.dto

import com.studentcenter.weave.application.meetingTeam.vo.MeetingMemberDetailInfo
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.domain.user.vo.MbtiAffinityScore
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
    @Schema(description = "해당 팀과의 케미 점수 (1~5, 확인 불가능 할 경우 null)")
    val affinityScore: Int?
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
        val isUnivVerified: Boolean,
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
                    isUnivVerified = meetingMemberDetailInfo.isUnivVerified,
                )
            }
        }
    }

    companion object {
        fun of(
            team: MeetingTeam,
            members: List<MeetingMemberDetailInfo>,
            affinityScore: MbtiAffinityScore?
        ): MeetingTeamGetDetailResponse {
            return MeetingTeamGetDetailResponse(
                id = team.id,
                teamIntroduce = team.teamIntroduce.value,
                memberCount = team.memberCount,
                location = team.location,
                gender = team.gender,
                members = members.map { MeetingMemberDto.from(it) },
                status = team.status,
                affinityScore = affinityScore?.value
            )
        }
    }

}
