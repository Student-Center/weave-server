package com.studentcenter.weave.bootstrap.meetingTeam.dto

import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
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
    val members: List<MeetingMemberDto>
) {

    data class MeetingMemberDto(
        val id: UUID,
        val universityName: String,
        val mbti: String,
        val birthYear: Int,
        val role: MeetingMemberRole,
        val animalType: AnimalType?,
        val height: Int?,
    )

}
