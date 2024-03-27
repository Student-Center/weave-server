package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamMemberSummaryRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamCreateUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.impl.MeetingTeamDomainServiceImpl
import com.studentcenter.weave.application.user.port.inbound.QueryUserStub
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.vo.KakaoId
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import com.studentcetner.weave.support.lock.DistributedLockTestInitializer
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk

@DisplayName("MeetingTeamCreateApplicationServiceTest")
class MeetingTeamCreateApplicationServiceTest : DescribeSpec({

    val meetingTeamRepositorySpy = MeetingTeamRepositorySpy()
    val meetingMemberRepositorySpy = MeetingMemberRepositorySpy()
    val meetingTeamMemberSummaryRepositorySpy = MeetingTeamMemberSummaryRepositorySpy()
    val userQueryUseCaseMock = mockk<QueryUserStub>()
    val meetingTeamDomainService = MeetingTeamDomainServiceImpl(
        meetingTeamRepositorySpy,
        meetingMemberRepositorySpy,
        meetingTeamMemberSummaryRepositorySpy,
        userQueryUseCaseMock,
    )
    val sut = MeetingTeamCreateApplicationService(
        meetingTeamDomainService,
        userQueryUseCaseMock,
    )

    beforeTest {
        DistributedLockTestInitializer.mockExecutionByStatic()
    }

    afterTest {
        SecurityContextHolder.clearContext()
        meetingTeamRepositorySpy.clear()
        meetingMemberRepositorySpy.clear()
    }

    describe("미팅 팀 생성 유스케이스") {
        context("사용자가 카카오 아이디를 등록하지 않았으면") {
            it("예외를 발생시킨다.") {
                // arrange
                val teamIntroduce = TeamIntroduce("팀 소개")
                val memberCount = 3
                val location = Location.BUSAN
                val command = MeetingTeamCreateUseCase.Command(
                    teamIntroduce = teamIntroduce,
                    memberCount = memberCount,
                    location = location,
                )

                val userFixture: User = UserFixtureFactory.create()
                UserAuthenticationFixtureFactory
                    .create(userFixture)
                    .let { SecurityContextHolder.setContext(UserSecurityContext(it)) }
                every { userQueryUseCaseMock.getById(userFixture.id) } returns userFixture

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(command)
                }
            }
        }

        context("사용자가 로그인 한 상태이면") {
            it("해당 사용자가 팀장인 미팅 팀을 생성한다") {
                // arrange
                val teamIntroduce = TeamIntroduce("팀 소개")
                val memberCount = 3
                val location = Location.BUSAN
                val command = MeetingTeamCreateUseCase.Command(
                    teamIntroduce = teamIntroduce,
                    memberCount = memberCount,
                    location = location
                )

                val leaderUserFixture: User = UserFixtureFactory.create(kakaoId = KakaoId(("test")))
                UserAuthenticationFixtureFactory
                    .create(leaderUserFixture)
                    .let { SecurityContextHolder.setContext(UserSecurityContext(it)) }
                every { userQueryUseCaseMock.getById(leaderUserFixture.id) } returns leaderUserFixture

                // act
                sut.invoke(command)

                // assert
                val meetingTeam = meetingTeamRepositorySpy.getLast() shouldNotBe null
                val member = meetingMemberRepositorySpy.getByMeetingTeamIdAndUserId(
                    meetingTeam.id,
                    leaderUserFixture.id
                )
                member.meetingTeamId shouldBe meetingTeam.id
                member.userId shouldBe leaderUserFixture.id
            }
        }
    }

})
