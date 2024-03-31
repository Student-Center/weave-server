package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.properties.MeetingTeamInvitationPropertiesFixtureFactory
import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamInvitationRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamMemberSummaryRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meetingTeam.util.impl.MeetingTeamInvitationServiceImpl
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitationFixtureFactory
import com.studentcenter.weave.application.user.port.inbound.GetUserStub
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import com.studentcetner.weave.support.lock.DistributedLockTestInitializer
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe
import io.mockk.clearAllMocks
import org.junit.jupiter.api.DisplayName

@DisplayName("JoinMeetingTeam")
class JoinMeetingTeamServiceTest : DescribeSpec({

    val meetingTeamRepository = MeetingTeamRepositorySpy()
    val meetingMemberRepository = MeetingMemberRepositorySpy()
    val meetingTeamMemberSummaryRepository = MeetingTeamMemberSummaryRepositorySpy()
    val userQueryUseCase = GetUserStub()

    val meetingTeamInvitationRepositorySpy = MeetingTeamInvitationRepositorySpy()

    val meetingTeamInvitationService = MeetingTeamInvitationServiceImpl(
        meetingTeamInvitationProperties = MeetingTeamInvitationPropertiesFixtureFactory.create(),
        meetingTeamInvitationRepository = meetingTeamInvitationRepositorySpy,
    )

    val sut = JoinMeetingTeamService(
        meetingTeamRepository = meetingTeamRepository,
        meetingTeamInvitationService = meetingTeamInvitationService,
        getUser = userQueryUseCase,
    )

    beforeTest {
        DistributedLockTestInitializer.mockExecutionByStatic()
    }

    afterTest {
        meetingTeamRepository.clear()
        meetingMemberRepository.clear()
        meetingTeamMemberSummaryRepository.clear()
        meetingTeamInvitationRepositorySpy.clear()
        SecurityContextHolder.clearContext()
        clearAllMocks()
    }

    describe("미팅 팀 입장 요청") {
        context("유효하지 않은 코드를 통해 입장을 시도하는 경우") {
            it("에러가 발생한다.") {
                // arrange
                val meetingTeam = MeetingTeamFixtureFactory.create()

                UserFixtureFactory.create()
                    .let { UserAuthenticationFixtureFactory.create(it) }
                    .let { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val meetingTeamInvitation = meetingTeamInvitationService.create(meetingTeam.id)

                // act & assert
                shouldThrow<NoSuchElementException> {
                    sut.invoke(meetingTeamInvitation.invitationCode)
                }
            }
        }

        context("유효한 코드를 통해 입장을 시도하는 경우") {
            it("정상적으로 팀에 초대되어 합류한다.") {
                // arrange
                val currentUser = UserFixtureFactory.create()
                val meetingTeam = MeetingTeamFixtureFactory
                    .create()
                    .also { meetingTeamRepository.save(it) }

                UserAuthenticationFixtureFactory
                    .create(currentUser)
                    .let { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val meetingTeamInvitation = MeetingTeamInvitationFixtureFactory
                    .create(teamId = meetingTeam.id)
                    .also { meetingTeamInvitationRepositorySpy.save(it) }

                // act
                sut.invoke(meetingTeamInvitation.invitationCode)

                // assert
                val savedMeetingTeam = meetingTeamRepository.getById(meetingTeam.id)
                savedMeetingTeam.members.find { it.userId == currentUser.id } shouldNotBe null
            }
        }
    }

})
