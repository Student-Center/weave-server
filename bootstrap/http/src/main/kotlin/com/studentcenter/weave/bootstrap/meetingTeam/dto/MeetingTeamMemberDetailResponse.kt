package com.studentcenter.weave.bootstrap.meetingTeam.dto

import com.studentcenter.weave.application.meetingTeam.vo.MeetingMemberDetail
import com.studentcenter.weave.domain.user.vo.BirthYear
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "미팅 팀 멤버 상세 응답")
data class MeetingTeamMemberDetailResponse(
    val id: UUID,
    val user: UserDto,
) {

    data class UserDto(
        val id: UUID,
        val mbti: String,
        val animalType: String?,
        val height: Int?,
        val profileImages: List<String>,
        val birthYear: BirthYear,
        val isUnivVerified: Boolean,
    )

    companion object {

        fun from(meetingMemberDetail: MeetingMemberDetail): MeetingTeamMemberDetailResponse {
            return MeetingTeamMemberDetailResponse(
                id = meetingMemberDetail.userId,
                user = UserDto(
                    id = meetingMemberDetail.userId,
                    mbti = meetingMemberDetail.mbti.value,
                    animalType = meetingMemberDetail.animalType?.name,
                    height = meetingMemberDetail.height?.value,
                    profileImages = meetingMemberDetail.profileImages.map { it.imageUrl.value },
                    birthYear = meetingMemberDetail.birthYear,
                    isUnivVerified = meetingMemberDetail.isUnivVerified,
                )
            )
        }
    }

}
