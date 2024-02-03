package com.studentcenter.weave.domain.meeting.entity

import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import com.studentcenter.weave.support.common.uuid.UuidCreator
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

class MeetingTeamTest : FunSpec({

    (2..4).forEach() { memberCount ->
        test("팀원의 수는 최소 2명 최대 4명까지 가능하다 : $memberCount 명 - 성공") {
            // arrange
            val teamIntroduce = TeamIntroduce("팀 한줄 소개")
            val leaderUserId = UuidCreator.create()
            val memberUserIds = setOf(UuidCreator.create(), UuidCreator.create())
            val location = Location.BUSAN

            // act, assert
            shouldNotThrowAny {
                MeetingTeam.create(
                    teamIntroduce = teamIntroduce,
                    leaderUserId = leaderUserId,
                    memberUserIds = memberUserIds,
                    memberCount = memberCount,
                    location = location,
                )
            }
        }
    }

    listOf(1, 5).forEach() { memberCount ->
        test("팀원의 수는 최소 2명 최대 4명까지 가능하다 : $memberCount 명 - 실패") {
            // arrange
            val teamIntroduce = TeamIntroduce("팀 한줄 소개")
            val leaderUserId = UuidCreator.create()
            val memberUserIds = setOf(UuidCreator.create(), UuidCreator.create())
            val location = Location.BUSAN

            // act, assert
            shouldThrow<IllegalArgumentException> {
                MeetingTeam.create(
                    teamIntroduce = teamIntroduce,
                    leaderUserId = leaderUserId,
                    memberUserIds = memberUserIds,
                    memberCount = memberCount,
                    location = location,
                )
            }
        }
    }

})
