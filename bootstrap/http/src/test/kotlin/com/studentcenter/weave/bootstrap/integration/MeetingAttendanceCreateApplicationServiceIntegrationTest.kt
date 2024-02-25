package com.studentcenter.weave.bootstrap.integration

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meeting.service.application.MeetingAttendanceCreateApplicationService
import com.studentcenter.weave.application.meeting.service.domain.MeetingAttendanceDomainService
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import com.studentcenter.weave.domain.meeting.entity.MeetingFixtureFactory
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.shouldBe

@DisplayName("MeetingAttendanceCreateApplicationService Integration Test")
class MeetingAttendanceCreateApplicationServiceIntegrationTest(
    private val meetingDomainService: MeetingDomainService,
    private val meetingAttendanceDomainService: MeetingAttendanceDomainService,
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val sut : MeetingAttendanceCreateApplicationService,
) : IntegrationTestDescribeSpec({
    val user = UserFixtureFactory.create()
    val userAuthentication = UserAuthenticationFixtureFactory.create(user)
    var userTeam: MeetingTeam? = null
    var userTeamMember: MeetingMember? = null

    fun createMeetingTeamAndMembers(
        memberCount: Int,
        gender: Gender,
        isUserTeam: Boolean = false,
    ): Pair<MeetingTeam, List<MeetingMember>> {
        return MeetingTeamFixtureFactory.create(
            status = MeetingTeamStatus.PUBLISHED,
            memberCount = memberCount,
            gender = gender
        ).let { team ->
            meetingTeamDomainService.save(team)
            if (isUserTeam) userTeam = team
            val members = IntRange(0, memberCount - 1).map { i ->
                val memberUser = if (isUserTeam && i == 0) user
                else UserFixtureFactory.create(gender = gender)
                val role = if (i == 0) MeetingMemberRole.LEADER else MeetingMemberRole.MEMBER
                val member = meetingTeamDomainService.addMember(memberUser, team, role)
                if (isUserTeam && i == 0) userTeamMember = member
                member
            }.toList()

            team to members
        }
    }

    beforeTest {
        SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
    }

    afterTest {
        userTeam = null
        userTeamMember = null
    }

    describe("미팅 참여 정보 추가 유스케이스") {
        context("해당 미팅이 완료(혹은 취소된 경우)") {
            listOf(MeetingStatus.CANCELED, MeetingStatus.COMPLETED).forEach{ meetingStatus ->
                it("예외를 던진다: 미팅 상태($meetingStatus)") {
                    // arrange
                    val attendance = true
                    val meeting = MeetingFixtureFactory.create(status = meetingStatus).also {
                        meetingDomainService.save(it)
                    }

                    // act, assert
                    shouldThrow<CustomException> { sut.invoke(meeting.id, attendance) }
                }
            }
        }
    }

    context("해당 미팅에 참여하고 있지 않은 경우") {
        it("예외를 던진다.") {
            // arrange
            val attendance = true
            val memberCount = 2
            val requestingTeam = MeetingTeamFixtureFactory.create(
                status = MeetingTeamStatus.PUBLISHED,
                memberCount = memberCount,
            )
            val receivingTeam = MeetingTeamFixtureFactory.create(
                status = MeetingTeamStatus.PUBLISHED,
                memberCount = memberCount,
                gender = Gender.WOMAN
            )
            val meeting = MeetingFixtureFactory.create(
                requestingTeamId = requestingTeam.id,
                receivingTeamId = receivingTeam.id,
                status = MeetingStatus.PENDING
            ).also {
                meetingDomainService.save(it)
            }

            // act, assert
            shouldThrow<CustomException> { sut.invoke(meeting.id, attendance) }
        }
    }

    context("해당 미팅에 이미 참여의사를 표한 경우") {
        it("예외를 던진다.") {
            // arrange
            val attendance = true
            val memberCount = 2
            val (requestingTeam, _) = createMeetingTeamAndMembers(
                memberCount = memberCount,
                gender = Gender.MAN,
                isUserTeam = true
            )
            val (receivingTeam, _) = createMeetingTeamAndMembers(
                memberCount = memberCount,
                gender = Gender.WOMAN,
            )
            val meeting = MeetingFixtureFactory.create(
                requestingTeamId = requestingTeam.id,
                receivingTeamId = receivingTeam.id,
                status = MeetingStatus.PENDING
            ).also {
                meetingDomainService.save(it)
            }
            val meetingAttendance = MeetingAttendance(
                meetingId = meeting.id,
                meetingMemberId = userTeamMember?.id ?: UuidCreator.create(),
                isAttend = true,
            )
            meetingAttendanceDomainService.save(meetingAttendance)

            // act, assert
            shouldThrow<CustomException> { sut.invoke(meeting.id, attendance) }
        }
    }

    context("미팅 참여하는 경우") {
        it("참여 정보가 생성된다.") {
            // arrange
            val attendance = true
            val memberCount = 2
            val (requestingTeam, _) = createMeetingTeamAndMembers(
                memberCount = memberCount,
                gender = Gender.MAN,
                isUserTeam = true
            )
            val (receivingTeam, _) = createMeetingTeamAndMembers(
                memberCount = memberCount,
                gender = Gender.WOMAN,
            )
            val meeting = MeetingFixtureFactory.create(
                requestingTeamId = requestingTeam.id,
                receivingTeamId = receivingTeam.id,
                status = MeetingStatus.PENDING
            ).also {
                meetingDomainService.save(it)
            }

            // act
            sut.invoke(meeting.id, attendance)

            // assert
            meetingAttendanceDomainService.existsByMeetingIdAndMeetingMemberId(
                meetingId = meeting.id,
                meetingMemberId = userTeamMember!!.id,
            ) shouldBe true
        }
    }

    context("미팅 참여하는 경우, 마지막 참여라면") {
        it("미팅이 완료 상태로 변경된다.") {
            // arrange
            val attendance = true
            val memberCount = 2
            val (requestingTeam, requestingMembers) = createMeetingTeamAndMembers(
                memberCount = memberCount,
                gender = Gender.MAN,
                isUserTeam = true
            )
            val (receivingTeam, receivingMembers) = createMeetingTeamAndMembers(
                memberCount = memberCount,
                gender = Gender.WOMAN,
            )
            val meeting = MeetingFixtureFactory.create(
                requestingTeamId = requestingTeam.id,
                receivingTeamId = receivingTeam.id,
                status = MeetingStatus.PENDING
            ).also {
                meetingDomainService.save(it)
            }
            (requestingMembers + receivingMembers).forEach {
                if (it.userId == user.id) return@forEach
                MeetingAttendance(
                    meetingId = meeting.id,
                    meetingMemberId = it.id,
                    isAttend = attendance,
                ).also { attendance -> meetingAttendanceDomainService.save(attendance) }
            }

            // act
            sut.invoke(meeting.id, attendance)

            // assert
            meetingDomainService.getById(meeting.id).status shouldBe MeetingStatus.COMPLETED
        }
    }

    context("미팅 패스 하는 경우") {
        it("미팅이 패스 상태로 변경된다.") {
            // arrange
            val attendance = false
            val memberCount = 2
            val (requestingTeam, _) = createMeetingTeamAndMembers(
                memberCount = memberCount,
                gender = Gender.MAN,
                isUserTeam = true
            )
            val (receivingTeam, _) = createMeetingTeamAndMembers(
                memberCount = memberCount,
                gender = Gender.WOMAN,
            )
            val meeting = MeetingFixtureFactory.create(
                requestingTeamId = requestingTeam.id,
                receivingTeamId = receivingTeam.id,
                status = MeetingStatus.PENDING
            ).also {
                meetingDomainService.save(it)
            }

            // act
            sut.invoke(meeting.id, attendance)

            // assert
            meetingDomainService.getById(meeting.id).status shouldBe MeetingStatus.CANCELED
        }
    }

})
