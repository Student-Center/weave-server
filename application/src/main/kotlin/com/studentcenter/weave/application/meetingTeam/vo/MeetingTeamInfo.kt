package com.studentcenter.weave.application.meetingTeam.vo

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.user.entity.User

data class MeetingTeamInfo(
    val team: MeetingTeam,
    val memberInfos: List<MemberInfo>
) {

    companion object {

        fun of(
            team: MeetingTeam,
            memberInfos: List<MemberInfo>,
        ): MeetingTeamInfo {
            return MeetingTeamInfo(
                team = team,
                memberInfos = memberInfos,
            )
        }

    }

    data class MemberInfo(
        val user: User,
        val university: University,
        val role: MeetingMemberRole,
        val isMe: Boolean,
    ) {

        companion object {

            fun of(
                user: User,
                university: University,
                role: MeetingMemberRole,
                isMe: Boolean,
            ): MemberInfo {
                return MemberInfo(
                    user = user,
                    university = university,
                    role = role,
                    isMe = isMe,
                )
            }

        }

    }

}
