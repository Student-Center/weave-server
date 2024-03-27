package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.properties.MeetingTeamInvitationPropertiesFixtureFactory
import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamInvitationRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamMemberSummaryRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meetingTeam.service.domain.impl.MeetingTeamDomainServiceImpl
import com.studentcenter.weave.application.meetingTeam.util.impl.MeetingTeamInvitationServiceImpl
import com.studentcenter.weave.application.user.port.inbound.GetUserStub
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import com.studentcetner.weave.support.lock.DistributedLockTestInitializer
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import org.junit.jupiter.api.DisplayName

@DisplayName("MeetingTeamEnterApplicationServiceTest")
class MeetingTeamEnterApplicationServiceTest : DescribeSpec({

    val meetingTeamRepository = MeetingTeamRepositorySpy()
    val meetingMemberRepository = MeetingMemberRepositorySpy()
    val meetingTeamMemberSummaryRepository = MeetingTeamMemberSummaryRepositorySpy()
    val userQueryUseCase = GetUserStub()

    val meetingTeamInvitationRepositorySpy = MeetingTeamInvitationRepositorySpy()

    val meetingTeamDomainService = MeetingTeamDomainServiceImpl(
        meetingTeamRepository = meetingTeamRepository,
        meetingMemberRepository = meetingMemberRepository,
        meetingTeamMemberSummaryRepository = meetingTeamMemberSummaryRepository,
        getUser = userQueryUseCase,
    )

    val meetingTeamInvitationService = MeetingTeamInvitationServiceImpl(
        meetingTeamInvitationProperties = MeetingTeamInvitationPropertiesFixtureFactory.create(),
        meetingTeamInvitationRepository = meetingTeamInvitationRepositorySpy,
    )

    val sut = MeetingTeamEnterApplicationService(
        meetingTeamDomainService = meetingTeamDomainService,
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
                val meetingTeam = MeetingTeamFixtureFactory.create()
                    .also { meetingTeamRepository.save(it) }

                UserAuthenticationFixtureFactory.create(currentUser)
                    .let { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val meetingTeamInvitation = meetingTeamInvitationService.create(meetingTeam.id)

                // act
                sut.invoke(meetingTeamInvitation.invitationCode)

                // assert
                val member =
                    meetingMemberRepository.getByMeetingTeamIdAndUserId(
                        meetingTeamId = meetingTeam.id,
                        userId = currentUser.id
                    )

                member.meetingTeamId shouldBe meetingTeam.id
            }
        }

        context("팀에 마지막 멤버가 합류한 경우") {
            it("팀의 상태를 PUBLISH로 전환한다.") {
                // arrange
                val memberCount = 2
                val leaderUser = UserFixtureFactory.create()
                val currentUser = UserFixtureFactory.create()
                val meetingTeam = MeetingTeamFixtureFactory.create(memberCount = memberCount)
                    .also { meetingTeamRepository.save(it) }

                val meetingTeamMember = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = leaderUser.id,
                    role = MeetingMemberRole.LEADER
                )
                meetingMemberRepository.save(meetingTeamMember)

                UserAuthenticationFixtureFactory.create(currentUser)
                    .let { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val meetingTeamInvitation = meetingTeamInvitationService.create(meetingTeam.id)

                // act
                sut.invoke(meetingTeamInvitation.invitationCode)

                // assert
                meetingTeamDomainService.getById(meetingTeam.id).status shouldBe MeetingTeamStatus.PUBLISHED
            }
        }

        context("정원이 가득 찬 팀에 입장하려는 경우") {
            it("에러가 발생한다.") {
                // arrange
                val memberCount = 2
                val leaderUser = UserFixtureFactory.create()
                val user1 = UserFixtureFactory.create()
                val currentUser = UserFixtureFactory.create()
                val meetingTeam = MeetingTeamFixtureFactory.create(memberCount = memberCount)
                    .also { meetingTeamRepository.save(it) }

                val meetingTeamMember1 = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = leaderUser.id,
                    role = MeetingMemberRole.LEADER
                )
                val meetingTeamMember2 = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = user1.id,
                    role = MeetingMemberRole.MEMBER
                )

                meetingMemberRepository.save(meetingTeamMember1)
                meetingMemberRepository.save(meetingTeamMember2)

                UserAuthenticationFixtureFactory.create(currentUser)
                    .let { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val meetingTeamInvitation = meetingTeamInvitationService.create(meetingTeam.id)

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(meetingTeamInvitation.invitationCode)
                }
            }
        }
    }

})
