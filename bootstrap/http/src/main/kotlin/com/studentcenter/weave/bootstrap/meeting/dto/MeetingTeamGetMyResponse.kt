package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.application.meeting.vo.MeetingTeamInfo
import com.studentcenter.weave.domain.meeting.enums.MeetingMemberRole
import com.studentcenter.weave.support.common.dto.ScrollResponse
import java.util.*

data class MeetingTeamGetMyResponse(
    override val item: List<MeetingTeamDto>,
    override val next: UUID?,
    override val total: Int,
) : ScrollResponse<MeetingTeamGetMyResponse.MeetingTeamDto, UUID?>(
    item = item,
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
                meetingTeamInfo: MeetingTeamInfo,
            ): MeetingTeamDto {
                return MeetingTeamDto(
                    id = meetingTeamInfo.team.id,
                    teamIntroduce = meetingTeamInfo.team.teamIntroduce.value,
                    memberCount = meetingTeamInfo.team.memberCount,
                    location = meetingTeamInfo.team.location.value,
                    memberInfos = meetingTeamInfo.memberInfos.map { MeetingMemberDto.from(it) },
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
                memberInfo: MeetingTeamInfo.MemberInfo,
            ): MeetingMemberDto {
                return MeetingMemberDto(
                    id = memberInfo.user.id,
                    universityName = memberInfo.university.name.value,
                    mbti = memberInfo.user.mbti.value,
                    birthYear = memberInfo.user.birthYear.value,
                    role = memberInfo.role,
                    isMe = memberInfo.isMe,
                )
            }

        }

    }

}
