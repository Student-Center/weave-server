package com.studentcenter.weave.domain.meeting.entity

import com.studentcenter.weave.domain.meeting.enums.TeamType
import com.studentcenter.weave.domain.meeting.vo.Location
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.util.*

data class MeetingTeam(
    val id: UUID = UuidCreator.create(),
    val teamIntroduce: TeamIntroduce,
    val memberUserIds: Set<UUID>,
    val leaderUserId: UUID,
    val teamType: TeamType,
    val location: Location,
) {

    init {
        require(teamType.peopleCount >= memberUserIds.size + LEADER_COUNT) { "팀원 수가 팀 타입의 최대 인원 수를 초과합니다." }
    }

    companion object {

        const val LEADER_COUNT = 1

        fun create(
            teamIntroduce: TeamIntroduce,
            leaderUserId: UUID,
            memberUserIds: Set<UUID>,
            teamType: TeamType,
            location: Location,
        ): MeetingTeam {
            return MeetingTeam(
                teamIntroduce = teamIntroduce,
                memberUserIds = memberUserIds,
                leaderUserId = leaderUserId,
                teamType = teamType,
                location = location,
            )
        }
    }
}
