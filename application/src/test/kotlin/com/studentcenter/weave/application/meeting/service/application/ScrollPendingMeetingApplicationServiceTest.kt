package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meeting.outbound.MeetingRepositorySpy
import com.studentcenter.weave.application.meeting.port.inbound.ScrollPendingMeetingUseCase
import com.studentcenter.weave.application.meeting.service.domain.impl.MeetingDomainServiceImpl
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamInfoGetAllByIdUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamQueryUseCase
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import com.studentcenter.weave.application.meetingTeam.vo.MemberInfo
import com.studentcenter.weave.application.university.port.outbound.UniversityRepositorySpy
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meeting.entity.MeetingFixtureFactory
import com.studentcenter.weave.domain.meeting.enums.TeamType
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole.LEADER
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole.MEMBER
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.university.vo.UniversityName
import com.studentcenter.weave.domain.user.entity.UniversityFixtureFactory
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlin.random.Random
import kotlin.random.nextUInt

@DisplayName("ScrollPendingMeetingApplicationServiceTest")
class ScrollPendingMeetingApplicationServiceTest : DescribeSpec({

    val meetingRepositorySpy = MeetingRepositorySpy()
    val meetingDomainService = MeetingDomainServiceImpl(
        meetingRepository = meetingRepositorySpy,
    )
    val meetingTeamQueryUseCase = mockk<MeetingTeamQueryUseCase>()
    val meetingTeamInfoGetAllByIdsUseCase = mockk<MeetingTeamInfoGetAllByIdUseCase>()

    val sut = ScrollPendingMeetingApplicationService(
        meetingDomainService = meetingDomainService,
        meetingTeamQueryUseCase = meetingTeamQueryUseCase,
        meetingTeamInfoGetAllByIdsUseCase = meetingTeamInfoGetAllByIdsUseCase,
    )

    val univRepo = UniversityRepositorySpy()

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
                val command = ScrollPendingMeetingUseCase.Command(
                    teamType = TeamType.REQUESTING,
                    next = null,
                    limit = 20,
                )
                every { meetingTeamQueryUseCase.findByMemberUserId(user.id) } returns null

                // act, assert
                shouldThrow<CustomException> { sut.invoke(command) }
            }
        }

        context("내가 요청하거나 받은 요청이 없는 경우") {
            enumValues<TeamType>().forEach { teamType ->
                it("TeamType(${teamType}): 빈 리스트를 반환한다.") {
                    // arrange
                    val user = createUser()
                    val command = ScrollPendingMeetingUseCase.Command(
                        teamType = TeamType.REQUESTING,
                        next = null,
                        limit = 20,
                    )
                    every { meetingTeamQueryUseCase.findByMemberUserId(user.id) } returns MeetingTeamFixtureFactory.create()
                    every { meetingTeamInfoGetAllByIdsUseCase.invoke(any()) } returns emptyList()

                    // act
                    val result = sut.invoke(command)

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
                    val command = ScrollPendingMeetingUseCase.Command(
                        teamType = teamType,
                        next = null,
                        limit = limit,
                    )

                    val myTeamInfo = createMeetingTeamInfo(users = listOf(user), memberCount = 2)
                    val teamInfos = MutableList(1) { myTeamInfo }
                    repeat(limit + 1) {
                        val teamInfo = createMeetingTeamInfo(gender = Gender.WOMAN)
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

                    every { meetingTeamQueryUseCase.findByMemberUserId(user.id) } returns myTeamInfo.team
                    every { meetingTeamInfoGetAllByIdsUseCase.invoke(any()) } returns teamInfos

                    // act
                    val result = sut.invoke(command)

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
                    val command = ScrollPendingMeetingUseCase.Command(
                        teamType = teamType,
                        next = null,
                        limit = limit,
                    )

                    val myTeamInfo = createMeetingTeamInfo(users = listOf(user), memberCount = 2)
                    val teamInfos = MutableList(1) { myTeamInfo }
                    repeat(count) {
                        val teamInfo = createMeetingTeamInfo(gender = Gender.WOMAN)
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

                    every { meetingTeamQueryUseCase.findByMemberUserId(user.id) } returns myTeamInfo.team
                    every { meetingTeamInfoGetAllByIdsUseCase.invoke(any()) } returns teamInfos

                    // act
                    val result = sut.invoke(command)

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

private fun createMeetingTeamInfo(
    users: List<User> = emptyList(),
    memberCount: Int = 2,
    gender: Gender = Gender.MAN,
): MeetingTeamInfo {
    val g = users.distinctBy { it.gender }
    require(g.size <= 1) { "성별이 다른 멤버로 팀을 만들 수 없어요" }
    require(users.isEmpty() || users[0].gender == gender ) { "멤버와 성별이 다를 수 없어요" }
    val team = MeetingTeamFixtureFactory.create(
        memberCount = memberCount,
        status = MeetingTeamStatus.PUBLISHED,
        gender = gender
    )
    val members = MutableList(users.size) { i ->
        MemberInfo(
            id = UuidCreator.create(),
            user = users[i],
            university = UniversityFixtureFactory.create(
                id = users[i].universityId,
                name = UniversityName(value = "${Random.nextUInt()}대학교"),
            ),
            role = if (i == 0) LEADER else MEMBER
        )
    }

    if (users.size < memberCount) {
        repeat(memberCount - users.size) { i ->
            val user = UserFixtureFactory.create(gender = gender)
            members.add(
                MemberInfo(
                    id = UuidCreator.create(),
                    user = user,
                    university = UniversityFixtureFactory.create(
                        id = user.universityId,
                        name = UniversityName(value = "${Random.nextUInt()}대학교"),
                    ),
                    role = if (users.isEmpty() && i == 0) LEADER else MEMBER
                )
            )
        }
    }

    return MeetingTeamInfo(
        team = team,
        memberInfos = members
    )
}

private fun createUser(): User {
    val user = UserFixtureFactory.create()
    val userAuthentication = UserAuthenticationFixtureFactory.create(user)
    SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
    return user
}
