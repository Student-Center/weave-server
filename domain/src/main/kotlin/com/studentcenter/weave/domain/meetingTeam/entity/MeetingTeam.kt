package com.studentcenter.weave.domain.meetingTeam.entity

import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.util.*

data class MeetingTeam(
    val id: UUID = UuidCreator.create(),
    val teamIntroduce: TeamIntroduce,
    val memberCount: Int,
    val location: Location,
    val status: MeetingTeamStatus,
    val gender: Gender,
) {

    init {
        require(memberCount in 2..4) {
            "미팅할 팀원의 수는 최소 2명에서 최대 4명까지 가능해요"
        }
    }

    fun update(
        teamIntroduce: TeamIntroduce?,
        memberCount: Int?,
        location: Location?,
    ): MeetingTeam {
        return copy(
            teamIntroduce = teamIntroduce ?: this.teamIntroduce,
            memberCount = memberCount ?: this.memberCount,
            location = location ?: this.location,
        )
    }

    companion object {

        fun create(
            teamIntroduce: TeamIntroduce,
            memberCount: Int,
            location: Location,
            gender: Gender
        ): MeetingTeam {
            return MeetingTeam(
                teamIntroduce = teamIntroduce,
                memberCount = memberCount,
                location = location,
                status = MeetingTeamStatus.WAITING,
                gender = gender,
            )
        }
    }

}
