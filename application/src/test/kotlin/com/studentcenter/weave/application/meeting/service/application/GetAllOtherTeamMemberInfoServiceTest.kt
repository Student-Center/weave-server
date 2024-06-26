package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meeting.outbound.MeetingRepositorySpy
import com.studentcenter.weave.application.meeting.service.domain.impl.MeetingDomainServiceImpl
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.application.university.port.inbound.GetUniversity
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meeting.entity.MeetingFixtureFactory
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import com.studentcenter.weave.domain.meeting.exception.MeetingException
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.user.entity.UniversityFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import java.util.stream.IntStream

@DisplayName("GetAllOtherTeamMemberInfoServiceTest")
class GetAllOtherTeamMemberInfoServiceTest : DescribeSpec({

    val meetingRepositorySpy = MeetingRepositorySpy()
    val meetingDomainService = MeetingDomainServiceImpl(
        meetingRepository = meetingRepositorySpy
    )
    val getMeetingTeam = mockk<GetMeetingTeam>()
    val getUser = mockk<GetUser>()
    val getUniversity = mockk<GetUniversity>()
    val sut = GetAllOtherTeamMemberInfoService(
        meetingDomainService = meetingDomainService,
        getMeetingTeam = getMeetingTeam,
        getUser = getUser,
        getUniversity = getUniversity
    )

    afterEach {
        meetingRepositorySpy.clear()
        SecurityContextHolder.clearContext()
        clearAllMocks()
    }

    describe("미팅 상대팀 멤버 정보 조회") {
        context("내가 속한 미팅이 아니라면") {
            it("예외를 던진다.") {
                // arrange
                val user = UserFixtureFactory
                    .create()
                    .also {
                        val userAuthentication = UserAuthenticationFixtureFactory.create(user = it)
                        SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                    }
                val meeting = MeetingFixtureFactory.create()
                meetingRepositorySpy.save(meeting)

                every { getMeetingTeam.getByMemberUserId(user.id) } returns MeetingTeamFixtureFactory.create()

                // act, assert
                shouldThrow<IllegalArgumentException> { sut.invoke(meeting.id) }
            }
        }

        context("아직 매칭되지 않았다면") {
            it("예외를 던진다.") {
                // arrange
                val user = UserFixtureFactory.create().also {
                    val userAuthentication = UserAuthenticationFixtureFactory.create(user = it)
                    SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                }
                val myTeam = MeetingTeamFixtureFactory.create()
                val meeting = MeetingFixtureFactory.create(
                    requestingTeamId = myTeam.id,
                    status = MeetingStatus.PENDING,
                )
                meetingRepositorySpy.save(meeting)

                every { getMeetingTeam.getByMemberUserId(user.id) } returns myTeam

                // act, assert
                shouldThrow<MeetingException.IsNotCompletedMeeting> { sut.invoke(meeting.id) }
            }
        }

        context("정상 조회라면") {
            it("카카오톡 아이디가 조회된다.") {
                // arrange
                val currentUser = UserFixtureFactory.create()
                UserAuthenticationFixtureFactory.create(user = currentUser)
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val memberCount = 2
                val myTeam = MeetingTeamFixtureFactory.create(memberCount = memberCount)

                val otherTeamMembers = IntStream
                    .of(memberCount)
                    .mapToObj { UserFixtureFactory.create() }
                    .toList()

                val otherTeam = MeetingTeamFixtureFactory.create(
                    memberCount = memberCount,
                    members = otherTeamMembers
                )

                val meeting = MeetingFixtureFactory.create(
                    requestingTeamId = myTeam.id,
                    receivingTeamId = otherTeam.id,
                    status = MeetingStatus.COMPLETED,
                    finishedAt = LocalDateTime.now(),
                )
                meetingRepositorySpy.save(meeting)

                every { getMeetingTeam.getById(otherTeam.id) } returns otherTeam
                every { getMeetingTeam.getByMemberUserId(currentUser.id) } returns myTeam
                every { getUser.getById(any()) } returns UserFixtureFactory.create(gender = Gender.WOMAN)
                every { getUniversity.getById(any()) } returns UniversityFixtureFactory.create()

                // act
                val result = sut.invoke(meeting.id)

                // assert
                result.size shouldBeGreaterThan 0
            }
        }
    }

})
