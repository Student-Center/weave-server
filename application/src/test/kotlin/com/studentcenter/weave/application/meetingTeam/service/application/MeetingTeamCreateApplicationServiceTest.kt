package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamMemberSummaryRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamCreateUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.impl.MeetingTeamDomainServiceImpl
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCaseStub
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.mockk

@DisplayName("MeetingTeamCreateApplicationServiceTest")
class MeetingTeamCreateApplicationServiceTest : DescribeSpec({

    val meetingTeamRepositorySpy = MeetingTeamRepositorySpy()
    val meetingMemberRepositorySpy = MeetingMemberRepositorySpy()
    val meetingTeamMemberSummaryRepositorySpy = MeetingTeamMemberSummaryRepositorySpy()
    val userQueryUseCaseMock = mockk<UserQueryUseCaseStub>()
    val meetingTeamDomainService = MeetingTeamDomainServiceImpl(
        meetingTeamRepositorySpy,
        meetingMemberRepositorySpy,
        meetingTeamMemberSummaryRepositorySpy,
        userQueryUseCaseMock,
    )

    val userRepositorySpy = UserRepositorySpy()
    val userQueryUseCaseStub = UserQueryUseCaseStub()
    val sut = MeetingTeamCreateApplicationService(
        meetingTeamDomainService,
        userQueryUseCaseStub,
    )

    afterTest {
        SecurityContextHolder.clearContext()
        meetingTeamRepositorySpy.clear()
        meetingMemberRepositorySpy.clear()
        userRepositorySpy.clear()
    }

    describe("미팅 팀 생성 유스케이스") {
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

                val leaderUserFixture: User = UserFixtureFactory.create()
                userRepositorySpy.save(leaderUserFixture)
                UserAuthenticationFixtureFactory
                    .create(leaderUserFixture)
                    .let { SecurityContextHolder.setContext(UserSecurityContext(it)) }

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
