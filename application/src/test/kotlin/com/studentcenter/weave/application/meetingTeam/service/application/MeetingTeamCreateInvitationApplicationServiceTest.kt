package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.properties.MeetingTeamInvitationPropertiesFixtureFactory
import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamInvitationRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamCreateInvitationUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.impl.MeetingTeamDomainServiceImpl
import com.studentcenter.weave.application.meetingTeam.util.impl.MeetingTeamInvitationServiceImpl
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCaseStub
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import java.util.*

@DisplayName("MeetingTeamCreateInvitationApplicationServiceTest")
class MeetingTeamCreateInvitationApplicationServiceTest : DescribeSpec({

    val meetingTeamRepository = MeetingTeamRepositorySpy()
    val meetingMemberRepository = MeetingMemberRepositorySpy()
    val meetingTeamDomainService = MeetingTeamDomainServiceImpl(
        meetingTeamRepository = meetingTeamRepository,
        meetingMemberRepository = meetingMemberRepository,
    )

    val meetingTeamInvitationRepositorySpy = MeetingTeamInvitationRepositorySpy()
    val meetingTeamInvitationService = MeetingTeamInvitationServiceImpl(
        meetingTeamInvitationProperties = MeetingTeamInvitationPropertiesFixtureFactory.create(),
        meetingTeamInvitationRepository = meetingTeamInvitationRepositorySpy,
    )

    val userRepository = UserRepositorySpy()

    val sut = MeetingTeamCreateInvitationApplicationService(
        meetingTeamInvitationService = meetingTeamInvitationService,
        meetingTeamDomainService = meetingTeamDomainService,
        meetingMemberRepository = meetingMemberRepository,
    )

    afterTest {
        SecurityContextHolder.clearContext()
        meetingTeamRepository.clear()
        meetingMemberRepository.clear()
        meetingTeamInvitationRepositorySpy.clear()
        userRepository.clear()
    }

    describe("미팅 팀 새로운 팀원 초대") {
        fun createMember(meetingTeamId: UUID, userId: UUID, role: MeetingMemberRole) {
            val member = MeetingMember.create(
                meetingTeamId = meetingTeamId,
                userId = userId,
                role = role,
            )

            meetingMemberRepository.save(member)
        }

        context("현재 유저가 팀장인 경우") {
            it("초대 링크를 정상적으로 발급한다.") {
                // arrange
                val meetingTeam = MeetingTeamFixtureFactory.create()
                val currentUser = UserFixtureFactory.create()

                createMember(
                    meetingTeamId = meetingTeam.id,
                    userId = currentUser.id,
                    role = MeetingMemberRole.LEADER,
                )

                meetingTeamRepository.save(meetingTeam)

                val userAuthentication = UserAuthenticationFixtureFactory.create(currentUser)

                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act & assert
                val invitationCode = sut.invoke(
                    meetingTeamId = meetingTeam.id,
                )

                // assert
                invitationCode.shouldNotBeNull()
            }
        }

        context("현재 유저가 팀장이 아닌 경우") {
            it("에러가 발생한다.") {
                // arrange
                val meetingTeam = MeetingTeamFixtureFactory.create()
                val currentUser = UserFixtureFactory.create()
                val leaderUser = UserFixtureFactory.create()

                createMember(
                    meetingTeamId = meetingTeam.id,
                    userId = leaderUser.id,
                    role = MeetingMemberRole.LEADER,
                )

                meetingTeamRepository.save(meetingTeam)

                val userAuthentication = UserAuthenticationFixtureFactory.create(currentUser)

                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(
                        meetingTeamId = meetingTeam.id,
                    )
                }
            }
        }

        context("팀의 정원이 가득 찬 경우에 새로운 팀원을 초대하려는 경우") {
            it("에러가 발생한다.") {
                // arrange
                val meetingTeam = MeetingTeamFixtureFactory.create()
                val leaderUser = UserFixtureFactory.create()
                val user1 = UserFixtureFactory.create()
                val user2 = UserFixtureFactory.create()
                val user3 = UserFixtureFactory.create()

                createMember(
                    meetingTeamId = meetingTeam.id,
                    userId = leaderUser.id,
                    role = MeetingMemberRole.LEADER,
                )
                createMember(
                    meetingTeamId = meetingTeam.id,
                    userId = user1.id,
                    role = MeetingMemberRole.MEMBER,
                )
                createMember(
                    meetingTeamId = meetingTeam.id,
                    userId = user2.id,
                    role = MeetingMemberRole.MEMBER,
                )
                createMember(
                    meetingTeamId = meetingTeam.id,
                    userId = user3.id,
                    role = MeetingMemberRole.MEMBER,
                )

                meetingTeamRepository.save(meetingTeam)

                val userAuthentication = UserAuthenticationFixtureFactory.create(leaderUser)

                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(
                        meetingTeamId = meetingTeam.id,
                    )
                }
            }
        }

    }
})
