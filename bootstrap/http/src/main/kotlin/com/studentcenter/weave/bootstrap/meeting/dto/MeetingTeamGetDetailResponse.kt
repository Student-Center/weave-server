package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.domain.meeting.entity.MeetingMember
import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.enums.MeetingMemberRole
import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.user.entity.User
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
    ) {

        companion object {

            fun of(
                user: User,
                meetingMember: MeetingMember,
                university: University,
            ): MeetingMemberDto {
                return MeetingMemberDto(
                    id = user.id,
                    universityName = university.name.value,
                    mbti = user.mbti.value,
                    birthYear = user.birthYear.value,
                    role = meetingMember.role,
                    animalType = user.animalType,
                    height = user.height?.value
                )
            }

        }
    }

}
