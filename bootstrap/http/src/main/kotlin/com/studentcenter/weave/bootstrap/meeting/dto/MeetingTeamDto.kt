package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import java.util.*


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
