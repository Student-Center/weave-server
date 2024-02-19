package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.exception.MeetingTeamExceptionType
import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamMemberSummaryRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamLeaveUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.impl.MeetingTeamDomainServiceImpl
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk

@DisplayName("MeetingTeamLeaveApplicationService")
class MeetingTeamLeaveApplicationServiceTest : DescribeSpec({

    val meetingMemberRepository = MeetingMemberRepositorySpy()
    val meetingTeamRepository = MeetingTeamRepositorySpy()
    val meetingTeamMemberSummaryRepository = MeetingTeamMemberSummaryRepositorySpy()
    val userQueryUseCase = mockk<UserQueryUseCase>()
    val meetingTeamDomainService = MeetingTeamDomainServiceImpl(
        meetingMemberRepository = meetingMemberRepository,
        meetingTeamRepository = meetingTeamRepository,
        meetingTeamMemberSummaryRepository = meetingTeamMemberSummaryRepository,
        userQueryUseCase = userQueryUseCase,
    )
    val sut = MeetingTeamLeaveApplicationService(meetingTeamDomainService)

    afterEach {
        meetingMemberRepository.clear()
        meetingTeamRepository.clear()
        SecurityContextHolder.clearContext()
    }

    describe("미팅팀 나가기 유스케이스") {
        context("유저가 해당팀의 멤버이고, 팀원일때") {
            it("팀에서 나간다") {
                // arrange
                val meetingTeam = MeetingTeamFixtureFactory.create()
                val user = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(user)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                val meetingMember = MeetingMember.create(
                    userId = user.id,
                    meetingTeamId = meetingTeam.id,
                    role = MeetingMemberRole.MEMBER,
                )

                meetingMemberRepository.save(meetingMember)
                meetingTeamRepository.save(meetingTeam)

                // act
                sut.invoke(MeetingTeamLeaveUseCase.Command(meetingTeam.id))

                // assert
                val member =
                    meetingMemberRepository.findByMeetingTeamIdAndUserId(meetingTeam.id, user.id)
                member shouldBe null
            }
        }

        context("유저가 해당팀의 멤버가 아닐때") {
            it("팀원이 아니라는 예외를 발생시킨다.") {
                // arrange
                val meetingTeam = MeetingTeamFixtureFactory.create()
                val user = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(user)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                meetingTeamRepository.save(meetingTeam)

                // act, assert
                val exception: CustomException = shouldThrow<CustomException> {
                    sut.invoke(MeetingTeamLeaveUseCase.Command(meetingTeam.id))
                }
                exception.type shouldBe MeetingTeamExceptionType.IS_NOT_TEAM_MEMBER
            }
        }

        context("유저가 해당팀의 멤버이고, 팀장일때") {
            it("팀장은 나갈 수 없다는 예외를 발생시킨다.") {
                // arrange
                val meetingTeam = MeetingTeamFixtureFactory.create()
                val user = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(user)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                val meetingMember = MeetingMember.create(
                    userId = user.id,
                    meetingTeamId = meetingTeam.id,
                    role = MeetingMemberRole.LEADER,
                )

                meetingMemberRepository.save(meetingMember)
                meetingTeamRepository.save(meetingTeam)

                // act, assert
                val exception: CustomException = shouldThrow<CustomException> {
                    sut.invoke(MeetingTeamLeaveUseCase.Command(meetingTeam.id))
                }
                exception.type shouldBe MeetingTeamExceptionType.LEADER_CANNOT_LEAVE_TEAM
            }
        }
    }

})
