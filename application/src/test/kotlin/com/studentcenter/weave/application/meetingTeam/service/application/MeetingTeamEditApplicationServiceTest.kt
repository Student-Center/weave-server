package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamMemberSummaryRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamEditUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.impl.MeetingTeamDomainServiceImpl
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk

@DisplayName("MeetingTeamEditApplicationService")
class MeetingTeamEditApplicationServiceTest : DescribeSpec({

    val meetingTeamRepository = MeetingTeamRepositorySpy()
    val meetingMemberRepository = MeetingMemberRepositorySpy()
    val meetingTeamMemberSummaryRepository = MeetingTeamMemberSummaryRepositorySpy()
    val userQueryUseCase = mockk<UserQueryUseCase>()

    val meetingTeamDomainService = MeetingTeamDomainServiceImpl(
        meetingTeamRepository = meetingTeamRepository,
        meetingMemberRepository = meetingMemberRepository,
        meetingTeamMemberSummaryRepository = meetingTeamMemberSummaryRepository,
        userQueryUseCase = userQueryUseCase,
    )
    val sut = MeetingTeamEditApplicationService(
        meetingTeamDomainService = meetingTeamDomainService
    )

    afterEach {
        meetingTeamRepository.clear()
        meetingMemberRepository.clear()
        SecurityContextHolder.clearContext()
    }

    describe("미팀 팀 정보 수정") {
        context("현재 유저가 수정하려는 팀의 팀장이 아닌경우") {
            it("예외가 발생한다") {
                // arrange
                val meetingTeam = MeetingTeamFixtureFactory.create()
                val currentUser = UserFixtureFactory.create()
                val leaderUser = UserFixtureFactory.create()
                val meetingMember = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = leaderUser.id,
                    role = MeetingMemberRole.LEADER
                )
                meetingMemberRepository.save(meetingMember)
                meetingTeamRepository.save(meetingTeam)
                val userAuthentication = UserAuthenticationFixtureFactory.create(currentUser)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(
                        MeetingTeamEditUseCase.Command(
                            id = meetingTeam.id,
                            location = Location.INCHON,
                            memberCount = 4,
                            teamIntroduce = TeamIntroduce("팀 소개")
                        )
                    )
                }
            }
        }

        context("현재유저가 수정하려는 팀의 팀장인 경우") {
            it("팀 정보가 수정된다") {
                // arrange
                val targetLocation = Location.INCHON
                val targetMemberCount = 4
                val targetTeamIntroduce = TeamIntroduce("팀 소개")

                val meetingTeam = MeetingTeamFixtureFactory.create()
                val currentUser = UserFixtureFactory.create()
                val meetingMember = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = currentUser.id,
                    role = MeetingMemberRole.LEADER
                )
                meetingMemberRepository.save(meetingMember)
                meetingTeamRepository.save(meetingTeam)
                val userAuthentication = UserAuthenticationFixtureFactory.create(currentUser)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act
                sut.invoke(
                    MeetingTeamEditUseCase.Command(
                        id = meetingTeam.id,
                        location = targetLocation,
                        memberCount = targetMemberCount,
                        teamIntroduce = targetTeamIntroduce
                    )
                )

                // assert
                val updatedMeetingTeam = meetingTeamRepository.getById(meetingTeam.id)
                updatedMeetingTeam.location shouldBe targetLocation
                updatedMeetingTeam.memberCount shouldBe targetMemberCount
                updatedMeetingTeam.teamIntroduce shouldBe targetTeamIntroduce
            }
        }

        context("현재 유저가 팀장이고, 현재 팀원수보다 적개 팀 인원을 변경하면") {
            it("예외가 발생한다") {
                // arrange
                val meetingTeam = MeetingTeamFixtureFactory.create()
                val currentUser = UserFixtureFactory.create()
                val memberUser1 = UserFixtureFactory.create()
                val memberUser2 = UserFixtureFactory.create()

                val leaderMember = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = currentUser.id,
                    role = MeetingMemberRole.LEADER
                )
                val member1 = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = memberUser1.id,
                    role = MeetingMemberRole.MEMBER
                )
                val member2 = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = memberUser2.id,
                    role = MeetingMemberRole.MEMBER
                )

                meetingMemberRepository.save(leaderMember)
                meetingMemberRepository.save(member1)
                meetingMemberRepository.save(member2)
                meetingTeamRepository.save(meetingTeam)

                val userAuthentication = UserAuthenticationFixtureFactory.create(currentUser)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(
                        MeetingTeamEditUseCase.Command(
                            id = meetingTeam.id,
                            location = Location.INCHON,
                            memberCount = 2,
                            teamIntroduce = TeamIntroduce("팀 소개")
                        )
                    )
                }
            }
        }
    }

})
