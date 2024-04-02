package com.studentcenter.weave.domain.meetingTeam.entity

import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("MeetingTeam")
class MeetingTeamTest : DescribeSpec({

    describe("미팅팀 생성") {
        (2..4).forEach() {
            context("[성공] 팀 정원은 2~4명이어야 한다. / 현재 인원 - $it 명") {
                it("생성에 성공한다.") {
                    // arrange
                    val teamIntroduce = TeamIntroduce("팀 한줄 소개")
                    val location = Location.BUSAN
                    val leaderUser = UserFixtureFactory.create()

                    // act & assert
                    shouldNotThrowAny {
                        MeetingTeam.create(
                            teamIntroduce = teamIntroduce,
                            memberCount = it,
                            location = location,
                            leader = leaderUser,
                        )
                    }
                }
            }
        }

        listOf(1, 5).forEach {
            context("[실패] 팀 정원은 2~4명이어야 한다. / 현재 인원 - $it 명") {
                it("생성에 실패한다.") {
                    // arrange
                    val teamIntroduce = TeamIntroduce("팀 한줄 소개")
                    val location = Location.BUSAN
                    val leaderUser = UserFixtureFactory.create()

                    // act & assert
                    shouldThrow<IllegalArgumentException> {
                        MeetingTeam.create(
                            teamIntroduce = teamIntroduce,
                            memberCount = it,
                            location = location,
                            leader = leaderUser,
                        )
                    }
                }
            }
        }

        context("[성공] 팀 생성시 상태는 WAITING이다.") {
            it("생성에 성공한다.") {
                // arrange
                val teamIntroduce = TeamIntroduce("팀 한줄 소개")
                val location = Location.BUSAN
                val leaderUser = UserFixtureFactory.create()

                // act
                val meetingTeam = MeetingTeam.create(
                    teamIntroduce = teamIntroduce,
                    memberCount = 2,
                    location = location,
                    leader = leaderUser,
                )

                // assert
                meetingTeam.status shouldBe MeetingTeamStatus.WAITING
            }
        }
    }

})
