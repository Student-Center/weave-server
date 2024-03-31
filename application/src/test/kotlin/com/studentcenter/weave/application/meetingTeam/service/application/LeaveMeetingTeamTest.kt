package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meeting.port.inbound.CancelAllMeetingUseCase
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk

@DisplayName("LeaveMeetingTeamTest")
class LeaveMeetingTeamTest : DescribeSpec({

    val meetingMemberRepository = MeetingMemberRepositorySpy()
    val meetingTeamRepository = MeetingTeamRepositorySpy()
    val getUser = mockk<GetUser>()
    val cancelAllMeetingUseCase = mockk<CancelAllMeetingUseCase>(relaxed = true)
    val sut = LeaveMeetingTeamService(
        meetingTeamRepository = meetingTeamRepository,
        cancelAllMeetingUseCase = cancelAllMeetingUseCase,
        getUser = getUser,
    )

    afterEach {
        meetingMemberRepository.clear()
        meetingTeamRepository.clear()
        SecurityContextHolder.clearContext()
        clearAllMocks()
    }

    describe("미팅팀 나가기 유스케이스") {
        context("[성공] 유저가 해당팀의 멤버이고, 팀원일때") {
            it("팀에서 나간다") {
                // arrange
                val currentUser = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(currentUser)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                val leaderUser = UserFixtureFactory.create()
                val meetingTeam = MeetingTeamFixtureFactory.create(leader = leaderUser)

                meetingTeam
                    .joinMember(currentUser)
                    .also { meetingTeamRepository.save(it) }

                every { getUser.getById(any()) } returns currentUser

                // act
                sut.invoke(meetingTeam.id)

                // assert
                val savedMeetingTeam = meetingTeamRepository.getById(meetingTeam.id)
                savedMeetingTeam.members.none { it.userId == currentUser.id } shouldBe true
            }
        }

        context("[실패] 유저가 해당팀의 멤버가 아닐때") {
            it("팀원이 아니라는 예외를 발생시킨다.") {
                // arrange
                val meetingTeam = MeetingTeamFixtureFactory.create()
                meetingTeamRepository.save(meetingTeam)

                val user = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(user)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                every { getUser.getById(any()) } returns user

                // act, assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(meetingTeam.id)
                }
            }
        }

        context("[실패] 유저가 팀장일때") {
            it("팀장은 나갈 수 없다는 예외를 발생시킨다.") {
                // arrange
                val currentUser = UserFixtureFactory.create()
                every { getUser.getById(any()) } returns currentUser

                val meetingTeam = MeetingTeamFixtureFactory
                    .create(leader = currentUser)
                    .also { meetingTeamRepository.save(it) }

                val userAuthentication = UserAuthenticationFixtureFactory.create(currentUser)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act, assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(meetingTeam.id)
                }
            }
        }
    }

})
