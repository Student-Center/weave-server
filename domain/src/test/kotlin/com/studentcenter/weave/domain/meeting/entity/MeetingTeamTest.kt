package com.studentcenter.weave.domain.meeting.entity

import com.studentcenter.weave.domain.meeting.enums.TeamType
import com.studentcenter.weave.domain.meeting.vo.Location
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import com.studentcenter.weave.support.common.uuid.UuidCreator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import java.util.*

class MeetingTeamTest : FunSpec({

    enumValues<TeamType>().forEach { teamType ->
        test("팀원 수가 팀 타입의 최대 인원 수를 초과하면 예외가 발생한다. teamType: $teamType") {
            val memberUserIds: Set<UUID> =
                (1..teamType.peopleCount).map { UuidCreator.create() }.toSet()
            val leaderUserId: UUID = UuidCreator.create()
            val location = Location("서울특별시 강남구")
            val teamIntroduce = TeamIntroduce("팀 소개")

            shouldThrow<IllegalArgumentException> {
                MeetingTeam.create(
                    teamIntroduce = teamIntroduce,
                    leaderUserId = leaderUserId,
                    memberUserIds = memberUserIds,
                    teamType = teamType,
                    location = location,
                )
            }
        }
    }

})
