package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.properties.MeetingTeamInvitationPropertiesFixtureFactory
import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamInvitationRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meetingTeam.util.impl.MeetingTeamInvitationServiceImpl
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("CreateInvitationLinkTest")
class CreateInvitationLinkTest : DescribeSpec({

    val meetingTeamRepository = MeetingTeamRepositorySpy()

    val meetingTeamInvitationRepositorySpy = MeetingTeamInvitationRepositorySpy()
    val meetingTeamInvitationService = MeetingTeamInvitationServiceImpl(
        meetingTeamInvitationProperties = MeetingTeamInvitationPropertiesFixtureFactory.create(),
        meetingTeamInvitationRepository = meetingTeamInvitationRepositorySpy,
    )

    val userRepository = UserRepositorySpy()

    val sut = CreateInvitationLinkService(
        meetingTeamInvitationService = meetingTeamInvitationService,
        meetingTeamRepository = meetingTeamRepository,
    )

    afterTest {
        SecurityContextHolder.clearContext()
        meetingTeamRepository.clear()
        meetingTeamInvitationRepositorySpy.clear()
        userRepository.clear()
    }

    describe("미팅 팀 새로운 팀원 초대") {
        context("현재 유저가 팀장인 경우") {
            it("초대 링크를 정상적으로 발급한다.") {
                // arrange
                val currentUser = UserFixtureFactory.create()
                val meetingTeam = MeetingTeamFixtureFactory.create(leader = currentUser)


                meetingTeamRepository.save(meetingTeam)

                val userAuthentication = UserAuthenticationFixtureFactory.create(currentUser)

                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act
                val result = sut.invoke(meetingTeamId = meetingTeam.id)
                val targetMeetingTeamInvitation =
                    meetingTeamInvitationRepositorySpy.findByInvitationCode(result.meetingTeamInvitationCode)

                // assert
                result.meetingTeamInvitationLink shouldBe targetMeetingTeamInvitation.invitationLink
                result.meetingTeamInvitationCode shouldBe targetMeetingTeamInvitation.invitationCode
            }
        }

        context("현재 유저가 팀장이 아닌 경우") {
            it("에러가 발생한다.") {
                // arrange
                val currentUser = UserFixtureFactory.create()
                val leaderUser = UserFixtureFactory.create()

                val meetingTeam = MeetingTeamFixtureFactory.create(leader = leaderUser)

                meetingTeamRepository.save(meetingTeam)

                val userAuthentication = UserAuthenticationFixtureFactory.create(currentUser)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(meetingTeamId = meetingTeam.id)
                }
            }
        }

        context("팀의 정원이 가득 찼을 때, 새로운 초대 링크를 발급하려는 경우") {
            it("에러가 발생한다.") {
                // arrange
                val leaderUser = UserFixtureFactory.create()
                val memberUser1 = UserFixtureFactory.create()
                val memberUser2 = UserFixtureFactory.create()

                val meetingTeam = MeetingTeamFixtureFactory.create(
                    memberCount = 3,
                    leader = leaderUser,
                    members = listOf(memberUser1, memberUser2)
                ).also { meetingTeamRepository.save(it) }

                val userAuthentication = UserAuthenticationFixtureFactory.create(leaderUser)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(meetingTeamId = meetingTeam.id)
                }
            }
        }

    }
})
