package com.studentcenter.weave.domain.meeting.entity

import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.common.uuid.UuidCreator
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class MeetingTeamTest : FunSpec({

    (2..4).forEach() { memberCount ->
        test("팀원의 수는 최소 2명 최대 4명까지 가능하다 : $memberCount 명 - 성공") {
            // arrange
            val teamIntroduce = TeamIntroduce("팀 한줄 소개")
            val leaderUser = UserFixtureFactory.create()
            val memberUserIds = setOf(UuidCreator.create(), UuidCreator.create())
            val location = Location.BUSAN

            // act, assert
            shouldNotThrowAny {
                MeetingTeam.create(
                    teamIntroduce = teamIntroduce,
                    leaderUser = leaderUser,
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
            val leaderUser = UserFixtureFactory.create()
            val memberUserIds = setOf(UuidCreator.create(), UuidCreator.create())
            val location = Location.BUSAN

            // act, assert
            shouldThrow<IllegalArgumentException> {
                MeetingTeam.create(
                    teamIntroduce = teamIntroduce,
                    leaderUser = leaderUser,
                    memberUserIds = memberUserIds,
                    memberCount = memberCount,
                    location = location,
                )
            }
        }
    }

    test("미팅 팀 생성시 WAITING 상태로 생성된다.") {
        // arrange
        val teamIntroduce = TeamIntroduce("팀 한줄 소개")
        val leaderUser = UserFixtureFactory.create()
        val memberUserIds = emptySet<UUID>()
        val location = Location.BUSAN

        // act
        val meetingTeam = MeetingTeam.create(
            teamIntroduce = teamIntroduce,
            leaderUser = leaderUser,
            memberUserIds = memberUserIds,
            memberCount = 2,
            location = location,
        )

        // assert
        meetingTeam.status shouldBe MeetingTeamStatus.WAITING
    }

})
