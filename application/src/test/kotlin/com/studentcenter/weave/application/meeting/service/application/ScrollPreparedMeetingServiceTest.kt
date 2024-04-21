package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meeting.outbound.MeetingRepositorySpy
import com.studentcenter.weave.application.meeting.port.inbound.ScrollPreparedMeeting
import com.studentcenter.weave.application.meeting.service.domain.impl.MeetingDomainServiceImpl
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetAllMeetingTeamInfo
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfoCreator
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meeting.entity.MeetingFixtureFactory
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.exception.MeetingTeamException
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk

@DisplayName("ScrollPreparedMeetingServiceTest")
class ScrollPreparedMeetingServiceTest : DescribeSpec({

    val meetingRepositorySpy = MeetingRepositorySpy()
    val meetingDomainService = MeetingDomainServiceImpl(
        meetingRepository = meetingRepositorySpy,
    )
    val getMeetingTeam = mockk<GetMeetingTeam>()
    val meetingTeamInfoGetAllByIds = mockk<GetAllMeetingTeamInfo>()

    val sut = ScrollPreparedMeetingService(
        meetingDomainService = meetingDomainService,
        getMeetingTeam = getMeetingTeam,
        meetingTeamInfoGetAllByIds = meetingTeamInfoGetAllByIds,
    )

    afterEach {
        meetingRepositorySpy.clear()
        SecurityContextHolder.clearContext()
        clearAllMocks()
    }

    describe("미팅 준비 스크롤 유스케이스") {
        context("내 미팅팀이 존재하지 않는 경우") {
            it("예외가 발생한다") {
                // arrange
                val user = createUserAndSetAuthentication()
                val query = ScrollPreparedMeeting.Query(
                    next = null,
                    limit = 20,
                )
                every { getMeetingTeam.findByMemberUserId(user.id) } returns null

                // act, assert
                shouldThrow<MeetingTeamException.CanNotFindMyMeetingTeam> { sut.invoke(query) }
            }
        }

        context("준비중인 미팅이 없는 경우") {
            it("빈 리스트를 반환한다.") {
                // arrange
                val user = createUserAndSetAuthentication()
                val limit = 2
                val query = ScrollPreparedMeeting.Query(
                    next = null,
                    limit = limit,
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

        context("매칭된 팀이 충분한 경우") {
            it("limit 만큼 조회된다.") {
                // arrange
                val user = createUserAndSetAuthentication()
                val limit = 2
                val query = ScrollPreparedMeeting.Query(
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
                        MeetingFixtureFactory.create(
                            requestingTeamId = myTeamInfo.team.id,
                            receivingTeamId = teamInfo.team.id,
                            status = MeetingStatus.COMPLETED,
                        )
                    )
                }

                every { getMeetingTeam.findByMemberUserId(user.id) } returns myTeamInfo.team
                every { meetingTeamInfoGetAllByIds.invoke(any()) } returns teamInfos

                // act
                val result = sut.invoke(query)

                // assert
                result.next shouldNotBe null
                result.total shouldBe limit
            }
        }
    }

    context("보낸 요청이 부족한 경우") {
        it("데이터의 수만큼 조회된다") {
            // arrange
            val user = createUserAndSetAuthentication()
            val limit = 3
            val query = ScrollPreparedMeeting.Query(
                next = null,
                limit = limit,
            )

            val myTeamInfo = MeetingTeamInfoCreator.create(users = listOf(user), memberCount = 2)
            val teamInfos = MutableList(1) { myTeamInfo }
            repeat(limit - 1) {
                val teamInfo = MeetingTeamInfoCreator.create(gender = Gender.WOMAN)
                teamInfos.add(teamInfo)
                meetingRepositorySpy.save(
                    MeetingFixtureFactory.create(
                        requestingTeamId = myTeamInfo.team.id,
                        receivingTeamId = teamInfo.team.id,
                        status = MeetingStatus.COMPLETED,
                    )
                )
            }

            every { getMeetingTeam.findByMemberUserId(user.id) } returns myTeamInfo.team
            every { meetingTeamInfoGetAllByIds.invoke(any()) } returns teamInfos

            // act
            val result = sut.invoke(query)

            // assert
            result.next shouldBe null
            result.total shouldBe limit - 1
        }
    }
})

private fun createUserAndSetAuthentication(): User {
    return UserFixtureFactory.create().also {
        val userAuthentication = UserAuthenticationFixtureFactory.create(it)
        SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
    }
}
