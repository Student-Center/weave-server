package com.studentcenter.weave.bootstrap.meetingTeam.dto

import com.studentcenter.weave.application.meetingTeam.vo.MyMeetingTeamInfo
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.support.common.dto.ScrollResponse
import java.util.*

data class MeetingTeamGetMyResponse(
    override val items: List<MeetingTeamDto>,
    override val next: UUID?,
    override val total: Int,
) : ScrollResponse<MeetingTeamGetMyResponse.MeetingTeamDto, UUID?>(
    items = items,
    next = next,
    total = total,
) {

    data class MeetingTeamDto(
        val id: UUID,
        val teamIntroduce: String,
        val memberCount: Int,
        val location: String,
        val memberInfos: List<MeetingMemberDto>,
    ) {

        companion object {

            fun from(
                myMeetingTeamInfo: MyMeetingTeamInfo,
            ): MeetingTeamDto {
                return MeetingTeamDto(
                    id = myMeetingTeamInfo.team.id,
                    teamIntroduce = myMeetingTeamInfo.team.teamIntroduce.value,
                    memberCount = myMeetingTeamInfo.team.memberCount,
                    location = myMeetingTeamInfo.team.location.value,
                    memberInfos = myMeetingTeamInfo.memberInfos.map { MeetingMemberDto.from(it) },
                )
            }

        }

    }

    data class MeetingMemberDto(
        val id: UUID,
        val universityName: String,
        val mbti: String,
        val birthYear: Int,
        val role: MeetingMemberRole,
        val isMe: Boolean,
    ) {

        companion object {

            fun from(
                memberInfo: MyMeetingTeamInfo.MemberInfo,
            ): MeetingMemberDto {
                return MeetingMemberDto(
                    id = memberInfo.user.id,
                    universityName = memberInfo.university.displayName,
                    mbti = memberInfo.user.mbti.value,
                    birthYear = memberInfo.user.birthYear.value,
                    role = memberInfo.role,
                    isMe = memberInfo.isMe,
                )
            }

        }

    }

}
