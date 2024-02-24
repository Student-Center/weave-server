package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meeting.outbound.MeetingRepositorySpy
import com.studentcenter.weave.application.meeting.port.inbound.MeetingRequestUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainServiceImpl
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamQueryUseCase
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

@DisplayName("MeetingRequestApplicationService")
class MeetingRequestApplicationServiceTest : DescribeSpec({

    val meetingRepositorySpy = MeetingRepositorySpy()
    val meetingDomainService = MeetingDomainServiceImpl(
        meetingRepository = meetingRepositorySpy,
    )
    val meetingTeamQueryUseCase = mockk<MeetingTeamQueryUseCase>()
    val userQueryUseCase = mockk<UserQueryUseCase>()

    val meetingRequestApplicationService = MeetingRequestApplicationService(
        meetingTeamQueryUseCase = meetingTeamQueryUseCase,
        meetingDomainService = meetingDomainService,
        userQueryUseCase = userQueryUseCase,
    )

    afterEach {
        meetingRepositorySpy.clear()
        SecurityContextHolder.clearContext()
    }

    describe("미팅 요청 유스케이스") {
        context("내 미팅팀이 존재하지 않는 경우") {
            it("예외가 발생한다") {
                // arrange
                val receivingMeetingTeamId: UUID = UuidCreator.create()
                val userAuthentication: UserAuthentication = UserFixtureFactory
                    .create()
                    .let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                every { userQueryUseCase.isUserUniversityVerified(userAuthentication.userId) } returns true
                every { meetingTeamQueryUseCase.findByMemberUserId(userAuthentication.userId) } returns null

                // act, assert
                shouldThrow<IllegalArgumentException> {
                    meetingRequestApplicationService.invoke(
                        MeetingRequestUseCase.Command(receivingMeetingTeamId)
                    )
                }
            }
        }

        enumValues<Gender>().forEach { gender ->
            context("상대 팀이 동일한 성별일 경우 $gender") {
                it("예외가 발생한다.") {
                    // arrange
                    val myMeetingTeam: MeetingTeam =
                        MeetingTeamFixtureFactory.create(gender = gender)
                    val receivingMeetingTeam: MeetingTeam =
                        MeetingTeamFixtureFactory.create(gender = gender)

                    val userAuthentication: UserAuthentication = UserFixtureFactory
                        .create()
                        .let { UserAuthenticationFixtureFactory.create(it) }
                        .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                    every { userQueryUseCase.isUserUniversityVerified(userAuthentication.userId) } returns true
                    every { meetingTeamQueryUseCase.findByMemberUserId(userAuthentication.userId) } returns myMeetingTeam
                    every { meetingTeamQueryUseCase.getById(receivingMeetingTeam.id) } returns receivingMeetingTeam

                    // act, assert
                    shouldThrow<IllegalArgumentException> {
                        meetingRequestApplicationService.invoke(
                            MeetingRequestUseCase.Command(receivingMeetingTeam.id)
                        )
                    }
                }
            }
        }

        context("상대팀과 팀원 수가 맞지 않을 경우") {
            it("예외가 발생한다.") {
                // arrange
                val myMeetingTeam = MeetingTeamFixtureFactory.create(memberCount = 2)
                val receivingMeetingTeam = MeetingTeamFixtureFactory.create(memberCount = 3)

                val userAuthentication: UserAuthentication = UserFixtureFactory
                    .create()
                    .let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                every { userQueryUseCase.isUserUniversityVerified(userAuthentication.userId) } returns true
                every { meetingTeamQueryUseCase.findByMemberUserId(userAuthentication.userId) } returns myMeetingTeam
                every { meetingTeamQueryUseCase.getById(receivingMeetingTeam.id) } returns receivingMeetingTeam

                // act, assert
                shouldThrow<IllegalArgumentException> {
                    meetingRequestApplicationService.invoke(
                        MeetingRequestUseCase.Command(receivingMeetingTeam.id)
                    )
                }
            }
        }

        context("내팀이 공개되지 않았을 경우") {
            it("예외가 발생한다.") {
                // arrange
                val myMeetingTeam =
                    MeetingTeamFixtureFactory.create(status = MeetingTeamStatus.WAITING)
                val receivingMeetingTeam = MeetingTeamFixtureFactory.create()

                val userAuthentication: UserAuthentication = UserFixtureFactory
                    .create()
                    .let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                every { userQueryUseCase.isUserUniversityVerified(userAuthentication.userId) } returns true
                every { meetingTeamQueryUseCase.findByMemberUserId(userAuthentication.userId) } returns myMeetingTeam
                every { meetingTeamQueryUseCase.getById(receivingMeetingTeam.id) } returns receivingMeetingTeam

                // act, assert
                shouldThrow<IllegalArgumentException> {
                    meetingRequestApplicationService.invoke(
                        MeetingRequestUseCase.Command(receivingMeetingTeam.id)
                    )
                }
            }
        }

        context("유저가 이메일 인증이 되지 않은 경우") {
            it("예외가 발생한다") {
                // arrange
                val myMeetingTeam = MeetingTeamFixtureFactory.create()
                val receivingMeetingTeam = MeetingTeamFixtureFactory.create()

                val userAuthentication: UserAuthentication = UserFixtureFactory
                    .create()
                    .let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                every { userQueryUseCase.isUserUniversityVerified(userAuthentication.userId) } returns false

                // act, assert
                shouldThrow<IllegalArgumentException> {
                    meetingRequestApplicationService.invoke(
                        MeetingRequestUseCase.Command(receivingMeetingTeam.id)
                    )
                }
            }

        }

        context("요청이 유효할 경우") {
            it("미팅이 생성된다") {
                // arrange
                val myMeetingTeam = MeetingTeamFixtureFactory.create(
                    status = MeetingTeamStatus.PUBLISHED
                )
                val receivingMeetingTeam = MeetingTeamFixtureFactory.create(
                    gender = Gender.WOMAN,
                    status = MeetingTeamStatus.PUBLISHED
                )

                val userAuthentication: UserAuthentication = UserFixtureFactory
                    .create()
                    .let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                every { userQueryUseCase.isUserUniversityVerified(userAuthentication.userId) } returns true
                every { meetingTeamQueryUseCase.findByMemberUserId(userAuthentication.userId) } returns myMeetingTeam
                every { meetingTeamQueryUseCase.getById(receivingMeetingTeam.id) } returns receivingMeetingTeam

                // act
                MeetingRequestUseCase.Command(receivingMeetingTeam.id)
                    .let { meetingRequestApplicationService.invoke(it) }

                // assert
                meetingRepositorySpy.findByRequestingMeetingTeamIdAndReceivingMeetingTeamId(
                    myMeetingTeam.id,
                    receivingMeetingTeam.id
                ) shouldNotBe null
            }
        }
    }

})