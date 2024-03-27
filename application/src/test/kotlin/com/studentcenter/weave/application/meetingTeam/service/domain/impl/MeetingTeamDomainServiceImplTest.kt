package com.studentcenter.weave.application.meetingTeam.service.domain.impl

import com.studentcenter.weave.application.meetingTeam.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamMemberSummaryRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcetner.weave.support.lock.DistributedLockTestInitializer
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

@DisplayName("MeetingTeamDomainServiceImpl")
class MeetingTeamDomainServiceImplTest : DescribeSpec({

    val meetingTeamRepositorySpy = MeetingTeamRepositorySpy()
    val meetingMemberRepositorySpy = MeetingMemberRepositorySpy()
    val meetingTeamMemberSummaryRepositorySpy = MeetingTeamMemberSummaryRepositorySpy()
    val getUser = mockk<GetUser>()

    val sut = MeetingTeamDomainServiceImpl(
        meetingTeamRepository = meetingTeamRepositorySpy,
        meetingMemberRepository = meetingMemberRepositorySpy,
        meetingTeamMemberSummaryRepository = meetingTeamMemberSummaryRepositorySpy,
        getUser = getUser,
    )

    beforeTest {
        DistributedLockTestInitializer.mockExecutionByStatic()
    }

    afterEach {
        meetingTeamRepositorySpy.clear()
        meetingMemberRepositorySpy.clear()
    }

    describe("addMember") {
        context("미팅 멤버수가 초과될 경우") {
            it("팀원의 수가 초과되었다는 예외를 던진다") {
                // arrange
                val memberCount = 2
                val meetingTeam = MeetingTeamFixtureFactory.create(memberCount = memberCount)
                val user1 = UserFixtureFactory.create()
                val user2 = UserFixtureFactory.create()
                val targetUser = UserFixtureFactory.create()

                val meetingTeamMember1 = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = user1.id,
                    role = MeetingMemberRole.LEADER
                )
                val meetingTeamMember2 = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = user2.id,
                    role = MeetingMemberRole.MEMBER
                )

                meetingMemberRepositorySpy.save(meetingTeamMember1)
                meetingMemberRepositorySpy.save(meetingTeamMember2)

                // act, assert
                shouldThrow<IllegalArgumentException> {
                    sut.addMember(
                        user = targetUser,
                        meetingTeam = meetingTeam,
                        role = MeetingMemberRole.MEMBER
                    )
                }
            }
        }

        context("미팅 멤버수가 초과 되지 않았을 경우") {
            it("팀원을 추가한다") {
                // arrange
                val memberCount = 2
                val meetingTeam = MeetingTeamFixtureFactory.create(memberCount = memberCount)
                val user = UserFixtureFactory.create()

                // act
                sut.addMember(
                    user = user,
                    meetingTeam = meetingTeam,
                    role = MeetingMemberRole.MEMBER
                )

                // assert
                val member = meetingMemberRepositorySpy.getByMeetingTeamIdAndUserId(
                    meetingTeamId = meetingTeam.id,
                    userId = user.id
                )
                member.meetingTeamId shouldBe meetingTeam.id
                member.userId shouldBe user.id
            }
        }

        context("미팅 팀에 속해 있는 멤버의 경우") {
            it("이미 미팅 팀에 소속되어 있다는 예외를 던진다") {
                // arrange
                val memberCount = 2
                val meetingTeam = MeetingTeamFixtureFactory.create(memberCount = memberCount)
                val user = UserFixtureFactory.create()
                val meetingMember = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = user.id,
                    role = MeetingMemberRole.MEMBER
                )
                meetingMemberRepositorySpy.save(meetingMember)

                // act & assert
                shouldThrow<CustomException> {
                    sut.addMember(
                        user = user,
                        meetingTeam = meetingTeam,
                        role = MeetingMemberRole.MEMBER
                    )
                }
            }
        }

        context("미팅팀에 마지막 멤버가 합류하는 경우") {
            it("팀 멤버 요약정보를 생성하고, 미팅 팀을 공개한다.") {
                // arrange
                val memberCount = 2
                val meetingTeam = MeetingTeamFixtureFactory.create(memberCount = memberCount)
                val user1 = UserFixtureFactory.create()
                val user2 = UserFixtureFactory.create()
                val meetingMember1 = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = user1.id,
                    role = MeetingMemberRole.LEADER
                )

                every { getUser.getById(user1.id) } returns user1
                every { getUser.getById(user2.id) } returns user2

                meetingMemberRepositorySpy.save(meetingMember1)
                meetingTeamRepositorySpy.save(meetingTeam)

                // act
                sut.addMember(
                    user = user2,
                    meetingTeam = meetingTeam,
                    role = MeetingMemberRole.MEMBER,
                )

                // assert
                meetingTeamRepositorySpy.getById(meetingTeam.id).isPublished() shouldBe true
                shouldNotThrowAny {
                    meetingTeamMemberSummaryRepositorySpy.getByMeetingTeamId(
                        meetingTeam.id
                    )
                }
            }
        }
    }

})
