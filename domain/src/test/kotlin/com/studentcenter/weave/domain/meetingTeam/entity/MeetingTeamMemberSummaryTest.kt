package com.studentcenter.weave.domain.meetingTeam.entity


import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary.Companion.createSummary
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.vo.BirthYear
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("MeetingTeamMemberSummary")
class MeetingTeamMemberSummaryTest : DescribeSpec({

    describe("MeetingTeamMemberSummary 생성") {
        context("[성공] 팀에 속한 멤버가 존재할 때") {
            it("생성에 성공한다.") {
                // arrange
                val users: List<User> = listOf(
                    UserFixtureFactory.create(),
                    UserFixtureFactory.create(),
                    UserFixtureFactory.create(),
                )
                val meetingTeam = MeetingTeamFixtureFactory.create(
                    memberCount = 3, members = users
                )

                val getUsersByMeetingMembers = { members: List<MeetingMember> -> users }

                // act & assert
                shouldNotThrowAny {
                    meetingTeam.createSummary { getUsersByMeetingMembers(it) }
                }
            }
        }

        context("[성공] 가장 어린 멤버의 태어난 해와 가장 나이 많은 멤버의 태어난 해가 설정된다.") {
            it("생성에 성공한다.") {
                // arrange
                val youngestMemberBirthYear = BirthYear(2000)
                val oldestMemberBirthYear = BirthYear(1990)

                val users: List<User> = listOf(
                    UserFixtureFactory.create(birthYear = oldestMemberBirthYear),
                    UserFixtureFactory.create(birthYear = BirthYear(1995)),
                    UserFixtureFactory.create(birthYear = youngestMemberBirthYear),
                )
                val meetingTeam: MeetingTeam = MeetingTeamFixtureFactory.create(
                    memberCount = 3, members = users
                )

                val getUsersByMeetingMembers: (List<MeetingMember>) -> List<User> =
                    { members: List<MeetingMember> -> users }

                // act
                val summary: MeetingTeamMemberSummary =
                    meetingTeam.createSummary { getUsersByMeetingMembers(it) }

                // assert
                summary.youngestMemberBirthYear shouldBe youngestMemberBirthYear
                summary.oldestMemberBirthYear shouldBe oldestMemberBirthYear
            }
        }
    }
})
