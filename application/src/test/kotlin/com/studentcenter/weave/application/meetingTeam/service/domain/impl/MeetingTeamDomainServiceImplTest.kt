package com.studentcenter.weave.application.meetingTeam.service.domain.impl

import com.studentcenter.weave.application.meetingTeam.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamMemberSummaryRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
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
    val userQueryUseCase = mockk<UserQueryUseCase>()

    val sut = MeetingTeamDomainServiceImpl(
        meetingTeamRepository = meetingTeamRepositorySpy,
        meetingMemberRepository = meetingMemberRepositorySpy,
        meetingTeamMemberSummaryRepository = meetingTeamMemberSummaryRepositorySpy,
        userQueryUseCase = userQueryUseCase,
    )

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

        context("미팅 멤버가 이미 존재하는 경우") {
            it("해당 멤버를 반환한다.") {
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

                // act
                val result = sut.addMember(
                    user = user,
                    meetingTeam = meetingTeam,
                    role = MeetingMemberRole.MEMBER
                )

                // assert
                result shouldBe meetingMember
            }
        }
    }

    describe("publish") {
        context("미팅 팀에 소속된 멤버 수가 0일 경우") {
            it("예외를 발생시킨다.") {
                // arrange
                val meetingTeam = MeetingTeamFixtureFactory.create()
                every { userQueryUseCase.getById(any()) } returns UserFixtureFactory.create()
                meetingTeamRepositorySpy.save(meetingTeam)

                // act, assert
                shouldThrow<IllegalArgumentException> {
                    sut.publishById(meetingTeam.id)
                }
            }
        }

        context("미팅 팀에 소속된 멤버 수가 설정된 멤버수와 동일할 경우") {
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
                val meetingMember2 = MeetingMember.create(
                    meetingTeamId = meetingTeam.id,
                    userId = user2.id,
                    role = MeetingMemberRole.MEMBER
                )

                every { userQueryUseCase.getById(user1.id) } returns user1
                every { userQueryUseCase.getById(user2.id) } returns user2

                meetingMemberRepositorySpy.save(meetingMember1)
                meetingMemberRepositorySpy.save(meetingMember2)
                meetingTeamRepositorySpy.save(meetingTeam)

                // act
                val result: MeetingTeam = sut.publishById(meetingTeam.id)

                // assert
                result.isPublished() shouldBe true
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
