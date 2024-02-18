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
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull

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
    val userQueryUseCaseStub = UserQueryUseCaseStub()

    val sut = MeetingTeamCreateInvitationApplicationService(
        meetingTeamInvitationService = meetingTeamInvitationService,
        meetingTeamDomainService = meetingTeamDomainService,
        meetingMemberRepository = meetingMemberRepository,
        userQueryUseCase = userQueryUseCaseStub
    )

    afterTest {
        SecurityContextHolder.clearContext()
        meetingTeamRepository.clear()
        meetingMemberRepository.clear()
        meetingTeamInvitationRepositorySpy.clear()
        userRepository.clear()
    }

    describe("미팅 팀 새로운 팀원 초대") {
        context("현재 유저가 팀장인 경우") {
            it("초대 링크를 정상적으로 발급한다.") {
                // arrange
                val meetingTeam = MeetingTeamFixtureFactory.create()
                val currentUser = UserFixtureFactory.create()
                val meetingMember = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = currentUser.id,
                    role = MeetingMemberRole.LEADER,
                )

                meetingMemberRepository.save(meetingMember)
                meetingTeamRepository.save(meetingTeam)

                val userAuthentication = UserAuthentication(
                    userId = currentUser.id,
                    email = currentUser.email,
                    nickname = currentUser.nickname,
                    avatar = currentUser.avatar,
                )

                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act & assert
                val invitationCode = sut.invoke(
                    MeetingTeamCreateInvitationUseCase.Command(
                        meetingTeamId = meetingTeam.id,
                    )
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
                val meetingMember = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = leaderUser.id,
                    role = MeetingMemberRole.LEADER,
                )

                meetingMemberRepository.save(meetingMember)
                meetingTeamRepository.save(meetingTeam)

                val userAuthentication = UserAuthentication(
                    userId = currentUser.id,
                    email = currentUser.email,
                    nickname = currentUser.nickname,
                    avatar = currentUser.avatar,
                )

                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(
                        MeetingTeamCreateInvitationUseCase.Command(
                            meetingTeamId = meetingTeam.id,
                        )
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

                val leaderMember = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = leaderUser.id,
                    role = MeetingMemberRole.LEADER,
                )
                val member1 = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = user1.id,
                    role = MeetingMemberRole.MEMBER,
                )
                val member2 = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = user2.id,
                    role = MeetingMemberRole.MEMBER,
                )
                val member3 = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = user3.id,
                    role = MeetingMemberRole.MEMBER,
                )

                meetingMemberRepository.save(leaderMember)
                meetingMemberRepository.save(member1)
                meetingMemberRepository.save(member2)
                meetingMemberRepository.save(member3)
                meetingTeamRepository.save(meetingTeam)

                val userAuthentication = UserAuthentication(
                    userId = leaderUser.id,
                    email = leaderUser.email,
                    nickname = leaderUser.nickname,
                    avatar = leaderUser.avatar,
                )

                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(
                        MeetingTeamCreateInvitationUseCase.Command(
                            meetingTeamId = meetingTeam.id,
                        )
                    )
                }
            }
        }

    }

})
