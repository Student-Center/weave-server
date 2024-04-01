package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meetingTeam.port.inbound.EditMeetingTeam
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("EditMeetingTeamTest")
class EditMeetingTeamTest : DescribeSpec({

    val meetingTeamRepository = MeetingTeamRepositorySpy()
    val sut = EditMeetingTeamService(
        meetingTeamRepository = meetingTeamRepository,
    )

    afterEach {
        meetingTeamRepository.clear()
        SecurityContextHolder.clearContext()
    }

    describe("미팀 팀 정보 수정") {
        context("현재 유저가 수정하려는 팀의 팀장이 아닌경우") {
            it("예외가 발생한다") {
                // arrange
                val currentUser = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(currentUser)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                val leaderUser = UserFixtureFactory.create()
                val meetingTeam = MeetingTeamFixtureFactory.create(leader = leaderUser)
                meetingTeam
                    .joinMember(currentUser)
                    .also { meetingTeamRepository.save(it) }

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(
                        EditMeetingTeam.Command(
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
                val currentUser = UserFixtureFactory.create()

                val meetingTeam = MeetingTeamFixtureFactory.create(
                    leader = currentUser
                ).also { meetingTeamRepository.save(it) }

                val userAuthentication = UserAuthenticationFixtureFactory.create(currentUser)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act
                sut.invoke(
                    EditMeetingTeam.Command(
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

        context("현재 유저가 팀장이고, 현재 팀원수보다 적개 팀 정원을 변경하면") {
            it("예외가 발생한다") {
                // arrange
                val currentUser = UserFixtureFactory.create()
                val memberUser1 = UserFixtureFactory.create()
                val memberUser2 = UserFixtureFactory.create()

                val meetingTeam = MeetingTeamFixtureFactory.create(leader = currentUser)

                meetingTeam
                    .joinMember(memberUser1)
                    .joinMember(memberUser2)
                    .also { meetingTeamRepository.save(it) }

                val userAuthentication = UserAuthenticationFixtureFactory.create(currentUser)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(
                        EditMeetingTeam.Command(
                            id = meetingTeam.id,
                            location = Location.INCHON,
                            memberCount = 2,
                            teamIntroduce = TeamIntroduce("팀 소개")
                        )
                    )
                }
            }
        }

        context("현재 팀이 공개된 경우") {
            it("예외가 발생한다") {
                // arrange
                val currentUser = UserFixtureFactory.create()
                val meetingTeam = MeetingTeamFixtureFactory.create(
                    status = MeetingTeamStatus.PUBLISHED,
                    leader = currentUser
                ).also { meetingTeamRepository.save(it) }

                val userAuthentication = UserAuthenticationFixtureFactory.create(currentUser)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(
                        EditMeetingTeam.Command(
                            id = meetingTeam.id,
                            location = Location.INCHON,
                            memberCount = 4,
                            teamIntroduce = TeamIntroduce("팀 소개")
                        )
                    )
                }
            }
        }
    }

})
