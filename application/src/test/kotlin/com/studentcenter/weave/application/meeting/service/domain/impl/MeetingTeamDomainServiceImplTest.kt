package com.studentcenter.weave.application.meeting.service.domain.impl

import com.studentcenter.weave.application.meeting.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meeting.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.domain.meeting.entity.MeetingMember
import com.studentcenter.weave.domain.meeting.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meeting.enums.MeetingMemberRole
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("MeetingTeamDomainServiceImpl")
class MeetingTeamDomainServiceImplTest : DescribeSpec({

    val meetingTeamRepositorySpy = MeetingTeamRepositorySpy()
    val meetingMemberRepositorySpy = MeetingMemberRepositorySpy()
    val sut = MeetingTeamDomainServiceImpl(
        meetingTeamRepository = meetingTeamRepositorySpy,
        meetingMemberRepository = meetingMemberRepositorySpy,
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

})
