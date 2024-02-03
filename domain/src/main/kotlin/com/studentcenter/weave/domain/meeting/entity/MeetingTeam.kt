package com.studentcenter.weave.domain.meeting.entity

import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.util.*

data class MeetingTeam(
    val id: UUID = UuidCreator.create(),
    val teamIntroduce: TeamIntroduce,
    val memberUserIds: Set<UUID>,
    val memberCount: Int,
    val leaderUserId: UUID,
    val location: Location,
) {

    init {
        require(memberCount in 2..4) {
            "미팅할 팀원의 수는 최소 2명에서 최대 4명까지 가능해요"
        }
    }

    companion object {

        fun create(
            teamIntroduce: TeamIntroduce,
            leaderUserId: UUID,
            memberUserIds: Set<UUID>,
            memberCount: Int,
            location: Location,
        ): MeetingTeam {
            return MeetingTeam(
                teamIntroduce = teamIntroduce,
                memberUserIds = memberUserIds,
                memberCount = memberCount,
                leaderUserId = leaderUserId,
                location = location,
            )
        }
    }
}
