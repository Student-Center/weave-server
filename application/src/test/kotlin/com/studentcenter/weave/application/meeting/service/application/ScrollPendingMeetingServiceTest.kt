package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meeting.outbound.MeetingRepositorySpy
import com.studentcenter.weave.application.meeting.port.inbound.ScrollPendingMeeting
import com.studentcenter.weave.application.meeting.service.domain.impl.MeetingDomainServiceImpl
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetAllMeetingTeamInfo
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfoCreator
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meeting.entity.MeetingFixtureFactory
import com.studentcenter.weave.domain.meeting.enums.TeamType
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk

@DisplayName("ScrollPendingMeetingServiceTest")
class ScrollPendingMeetingServiceTest : DescribeSpec({

    val meetingRepositorySpy = MeetingRepositorySpy()
    val meetingDomainService = MeetingDomainServiceImpl(
        meetingRepository = meetingRepositorySpy,
    )
    val getMeetingTeam = mockk<GetMeetingTeam>()
    val meetingTeamInfoGetAllByIds = mockk<GetAllMeetingTeamInfo>()

    val sut = ScrollPendingMeetingService(
        meetingDomainService = meetingDomainService,
        getMeetingTeam = getMeetingTeam,
        meetingTeamInfoGetAllByIds = meetingTeamInfoGetAllByIds,
    )

    afterEach {
        meetingRepositorySpy.clear()
        SecurityContextHolder.clearContext()
        clearAllMocks()
    }

    describe("미팅 스크롤 유스케이스") {
        context("내 미팅팀이 존재하지 않는 경우") {
            it("예외가 발생한다") {
                // arrange
                val user = createUser()
                val query = ScrollPendingMeeting.Query(
                    teamType = TeamType.REQUESTING,
                    next = null,
                    limit = 20,
                )
                every { getMeetingTeam.findByMemberUserId(user.id) } returns null

                // act, assert
                shouldThrow<CustomException> { sut.invoke(query) }
            }
        }

        context("내가 요청하거나 받은 요청이 없는 경우") {
            enumValues<TeamType>().forEach { teamType ->
                it("TeamType(${teamType}): 빈 리스트를 반환한다.") {
                    // arrange
                    val user = createUser()
                    val query = ScrollPendingMeeting.Query(
                        teamType = TeamType.REQUESTING,
                        next = null,
                        limit = 20,
                    )
                    every { getMeetingTeam.findByMemberUserId(user.id) } returns MeetingTeamFixtureFactory.create()
                    every { meetingTeamInfoGetAllByIds.invoke(any()) } returns emptyList()

                    // act
                    val result = sut.invoke(query)

                    // assert
                    result.items.isEmpty() shouldBe true
                    result.next shouldBe null
                    result.total shouldBe 0
                }
            }
        }

        context("요청이 충분한 경우") {
            enumValues<TeamType>().forEach { teamType ->
                it("limit 만큼 조회된다.") {
                    // arrange
                    val user = createUser()
                    val limit = 2
                    val query = ScrollPendingMeeting.Query(
                        teamType = teamType,
                        next = null,
                        limit = limit,
                    )

                    val myTeamInfo =
                        MeetingTeamInfoCreator.create(users = listOf(user), memberCount = 2)
                    val teamInfos = MutableList(1) { myTeamInfo }
                    repeat(limit + 1) {
                        val teamInfo = MeetingTeamInfoCreator.create(gender = Gender.WOMAN)
                        teamInfos.add(teamInfo)
                        meetingRepositorySpy.save(
                            if (teamType == TeamType.REQUESTING) {
                                MeetingFixtureFactory.create(
                                    requestingTeamId = myTeamInfo.team.id,
                                    receivingTeamId = teamInfo.team.id,
                                )
                            } else {
                                MeetingFixtureFactory.create(
                                    requestingTeamId = teamInfo.team.id,
                                    receivingTeamId = myTeamInfo.team.id,
                                )
                            }
                        )
                    }

                    every { getMeetingTeam.findByMemberUserId(user.id) } returns myTeamInfo.team
                    every { meetingTeamInfoGetAllByIds.invoke(any()) } returns teamInfos

                    // act
                    val result = sut.invoke(query)

                    // assert
                    val uniqueTeamTypeTeamIds = result
                        .items
                        .map {
                            if (teamType == TeamType.REQUESTING) it.requestingTeam.team.id
                            else it.receivingTeam.team.id
                        }.distinct()
                    uniqueTeamTypeTeamIds.size shouldBe 1
                    uniqueTeamTypeTeamIds[0] shouldBe myTeamInfo.team.id
                    result.next shouldNotBe null
                    result.total shouldBe limit
                }
            }
        }

        context("보낸 요청이 부족한 경우") {
            enumValues<TeamType>().forEach { teamType ->
                it("데이터의 수만큼 조회된다") {
                    // arrange
                    val user = createUser()
                    val count = 10
                    val limit = count * 2
                    val query = ScrollPendingMeeting.Query(
                        teamType = teamType,
                        next = null,
                        limit = limit,
                    )

                    val myTeamInfo =
                        MeetingTeamInfoCreator.create(users = listOf(user), memberCount = 2)
                    val teamInfos = MutableList(1) { myTeamInfo }
                    repeat(count) {
                        val teamInfo = MeetingTeamInfoCreator.create(gender = Gender.WOMAN)
                        teamInfos.add(teamInfo)
                        meetingRepositorySpy.save(
                            if (teamType == TeamType.REQUESTING) {
                                MeetingFixtureFactory.create(
                                    requestingTeamId = myTeamInfo.team.id,
                                    receivingTeamId = teamInfo.team.id,
                                )
                            } else {
                                MeetingFixtureFactory.create(
                                    requestingTeamId = teamInfo.team.id,
                                    receivingTeamId = myTeamInfo.team.id,
                                )
                            }
                        )
                    }

                    every { getMeetingTeam.findByMemberUserId(user.id) } returns myTeamInfo.team
                    every { meetingTeamInfoGetAllByIds.invoke(any()) } returns teamInfos

                    // act
                    val result = sut.invoke(query)

                    // assert
                    val uniqueTeamTypeTeamIds = result
                        .items
                        .map {
                            if (teamType == TeamType.REQUESTING) it.requestingTeam.team.id
                            else it.receivingTeam.team.id
                        }.distinct()
                    uniqueTeamTypeTeamIds.size shouldBe 1
                    uniqueTeamTypeTeamIds[0] shouldBe myTeamInfo.team.id
                    result.next shouldBe null
                    result.total shouldBe count
                }
            }
        }
    }

})

private fun createUser(): User {
    val user = UserFixtureFactory.create()
    val userAuthentication = UserAuthenticationFixtureFactory.create(user)
    SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
    return user
}
