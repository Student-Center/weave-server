package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.domain.meeting.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.user.enums.Gender
import java.util.*

data class MeetingTeamGetMyResponse(
    val id: UUID,
    val teamIntroduce: String,
    val memberCount: Int,
    val leaderUserInfo: LeaderUserInfo,
    val memberUserInfos: List<MemberUserInfo>,
    val location: String,
    val gender: Gender,
    val status: MeetingTeamStatus,
) {

    data class LeaderUserInfo(
        val id: UUID,
        val universityName: String,
        val mbti: String,
        val birthYear: Int,
    )

    data class MemberUserInfo(
        val id: UUID,
        val universityName: String,
        val mbti: String,
        val birthYear: Int,
    )

}
