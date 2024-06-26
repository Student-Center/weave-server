package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meeting.outbound.MeetingAttendanceRepositorySpy
import com.studentcenter.weave.application.meeting.outbound.MeetingRepositorySpy
import com.studentcenter.weave.application.meeting.port.inbound.RequestMeeting
import com.studentcenter.weave.application.meeting.service.domain.impl.MeetingAttendanceDomainServiceImpl
import com.studentcenter.weave.application.meeting.service.domain.impl.MeetingDomainServiceImpl
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meeting.exception.MeetingException
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meetingTeam.exception.MeetingTeamException
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

@DisplayName("MeetingRequestService")
class MeetingRequestServiceTest : DescribeSpec({

    val meetingRepositorySpy = MeetingRepositorySpy()
    val meetingDomainService = MeetingDomainServiceImpl(
        meetingRepository = meetingRepositorySpy,
    )
    val meetingAttendanceRepositorySpy = MeetingAttendanceRepositorySpy()
    val meetingAttendanceDomainService = MeetingAttendanceDomainServiceImpl(
        meetingAttendanceRepository = meetingAttendanceRepositorySpy,
    )
    val getMeetingTeam = mockk<GetMeetingTeam>()
    val getUser = mockk<GetUser>()

    val meetingRequestService = RequestMeetingService(
        getMeetingTeam = getMeetingTeam,
        meetingDomainService = meetingDomainService,
        meetingAttendanceDomainService = meetingAttendanceDomainService,
        getUser = getUser,
    )

    afterEach {
        meetingAttendanceRepositorySpy.clear()
        meetingRepositorySpy.clear()
        SecurityContextHolder.clearContext()
    }

    describe("미팅 요청 유스케이스") {
        context("내 미팅팀이 존재하지 않는 경우") {
            it("예외가 발생한다") {
                // arrange
                val receivingMeetingTeamId: UUID = UuidCreator.create()
                val user: User = UserFixtureFactory.create(isUnivVerified = true)
                user.let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                every { getUser.getById(user.id) } returns user
                every { getMeetingTeam.findByMemberUserId(user.id) } returns null

                // act, assert
                shouldThrow<MeetingTeamException.CanNotFindMyMeetingTeam> {
                    meetingRequestService.invoke(
                        RequestMeeting.Command(receivingMeetingTeamId)
                    )
                }
            }
        }

        enumValues<Gender>().forEach { gender ->
            context("상대 팀이 동일한 성별일 경우 $gender") {
                it("예외가 발생한다.") {
                    // arrange
                    val myMeetingTeam: MeetingTeam =
                        MeetingTeamFixtureFactory.create(
                            gender = gender,
                            status = MeetingTeamStatus.PUBLISHED
                        )
                    val receivingMeetingTeam: MeetingTeam =
                        MeetingTeamFixtureFactory.create(
                            gender = gender,
                            status = MeetingTeamStatus.PUBLISHED
                        )

                    val user: User = UserFixtureFactory.create(isUnivVerified = true)
                    user.let { UserAuthenticationFixtureFactory.create(it) }
                        .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                    every { getUser.getById(user.id) } returns user
                    every { getMeetingTeam.findByMemberUserId(user.id) } returns myMeetingTeam
                    every { getMeetingTeam.getById(receivingMeetingTeam.id) } returns receivingMeetingTeam

                    // act, assert
                    shouldThrow<MeetingException.CanNotRequestToSameGender> {
                        meetingRequestService.invoke(
                            RequestMeeting.Command(receivingMeetingTeam.id)
                        )
                    }
                }
            }
        }

        context("상대팀과 팀원 수가 맞지 않을 경우") {
            it("예외가 발생한다.") {
                // arrange
                val myMeetingTeam = MeetingTeamFixtureFactory.create(
                    memberCount = 2,
                    status = MeetingTeamStatus.PUBLISHED,
                    gender = Gender.MAN,
                )
                val receivingMeetingTeam = MeetingTeamFixtureFactory.create(
                    memberCount = 3,
                    status = MeetingTeamStatus.PUBLISHED,
                    gender = Gender.WOMAN,
                )

                val user: User = UserFixtureFactory.create(isUnivVerified = true)
                user.let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                every { getUser.getById(user.id) } returns user
                every { getMeetingTeam.findByMemberUserId(user.id) } returns myMeetingTeam
                every { getMeetingTeam.getById(receivingMeetingTeam.id) } returns receivingMeetingTeam

                // act, assert
                shouldThrow<MeetingException.CanNotRequestToDifferentMemberCount> {
                    meetingRequestService.invoke(
                        RequestMeeting.Command(receivingMeetingTeam.id)
                    )
                }
            }
        }

        context("내팀이 공개되지 않았을 경우") {
            it("예외가 발생한다.") {
                // arrange
                val myMeetingTeam =
                    MeetingTeamFixtureFactory.create(status = MeetingTeamStatus.WAITING)
                val receivingMeetingTeam = MeetingTeamFixtureFactory.create()

                val user: User = UserFixtureFactory.create(isUnivVerified = true)
                user.let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                every { getUser.getById(user.id) } returns user
                every { getMeetingTeam.findByMemberUserId(user.id) } returns myMeetingTeam
                every { getMeetingTeam.getById(receivingMeetingTeam.id) } returns receivingMeetingTeam

                // act, assert
                shouldThrow<MeetingTeamException.IsNotPublishedTeam> {
                    meetingRequestService.invoke(
                        RequestMeeting.Command(receivingMeetingTeam.id)
                    )
                }
            }
        }

        context("유저가 이메일 인증이 되지 않은 경우") {
            it("예외가 발생한다") {
                // arrange
                val receivingMeetingTeam = MeetingTeamFixtureFactory.create()

                val user: User = UserFixtureFactory.create(isUnivVerified = false)
                user.let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                every { getUser.getById(user.id) } returns user
                every { getMeetingTeam.findByMemberUserId(user.id) } returns
                        MeetingTeamFixtureFactory.create(status = MeetingTeamStatus.PUBLISHED)

                // act, assert
                shouldThrow<MeetingException.UniversityMailUnverifiedUser> {
                    meetingRequestService.invoke(
                        RequestMeeting.Command(receivingMeetingTeam.id)
                    )
                }
            }

        }

        context("이미 신청한 경우") {
            it("예외를 던진다") {
                val me: User = UserFixtureFactory.create(isUnivVerified = true)
                me.let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val otherMemberUser = UserFixtureFactory.create()

                // arrange
                val myMeetingTeam = MeetingTeamFixtureFactory.create(
                    status = MeetingTeamStatus.PUBLISHED,
                    leader = me,
                    members = listOf(otherMemberUser)
                )
                val receivingMeetingTeam = MeetingTeamFixtureFactory.create(
                    gender = Gender.WOMAN,
                    status = MeetingTeamStatus.PUBLISHED
                )


                every { getUser.getById(me.id) } returns me
                every { getMeetingTeam.findByMemberUserId(me.id) } returns myMeetingTeam
                every { getMeetingTeam.getById(receivingMeetingTeam.id) } returns receivingMeetingTeam

                val command = RequestMeeting.Command(receivingMeetingTeam.id)
                meetingRequestService.invoke(command)

                // act, assert
                shouldThrow<MeetingException.AlreadyRequestMeeting> {
                    meetingRequestService.invoke(command)
                }
            }
        }

        context("요청이 유효할 경우") {
            it("미팅이 생성된다") {
                // arrange
                val me: User = UserFixtureFactory.create(isUnivVerified = true)
                me.let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val otherMemberUser = UserFixtureFactory.create()

                val myMeetingTeam = MeetingTeamFixtureFactory.create(
                    status = MeetingTeamStatus.PUBLISHED,
                    leader = me,
                    members = listOf(otherMemberUser)
                )

                val receivingMeetingTeam = MeetingTeamFixtureFactory.create(
                    gender = Gender.WOMAN,
                    status = MeetingTeamStatus.PUBLISHED
                )

                every { getUser.getById(me.id) } returns me
                every { getMeetingTeam.findByMemberUserId(me.id) } returns myMeetingTeam
                every { getMeetingTeam.getById(receivingMeetingTeam.id) } returns receivingMeetingTeam

                // act
                RequestMeeting.Command(receivingMeetingTeam.id)
                    .let { meetingRequestService.invoke(it) }

                // assert
                val meeting = meetingRepositorySpy
                    .findByRequestingTeamIdAndReceivingTeamId(
                        myMeetingTeam.id,
                        receivingMeetingTeam.id
                    )
                meeting shouldNotBe null
            }

            it("요청자는 미팅 참여도 생성된다") {
                // arrange
                val user: User = UserFixtureFactory.create(isUnivVerified = true)
                user.let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val otherMemberUser = UserFixtureFactory.create()

                val myMeetingTeam = MeetingTeamFixtureFactory.create(
                    status = MeetingTeamStatus.PUBLISHED,
                    leader = user,
                    members = listOf(otherMemberUser),
                )

                val receivingMeetingTeam = MeetingTeamFixtureFactory.create(
                    gender = Gender.WOMAN,
                    status = MeetingTeamStatus.PUBLISHED
                )

                every { getUser.getById(user.id) } returns user
                every { getMeetingTeam.findByMemberUserId(user.id) } returns myMeetingTeam
                every { getMeetingTeam.getById(receivingMeetingTeam.id) } returns receivingMeetingTeam

                // act
                RequestMeeting.Command(receivingMeetingTeam.id)
                    .let { meetingRequestService.invoke(it) }

                // assert
                val meeting = meetingRepositorySpy.findByRequestingTeamIdAndReceivingTeamId(
                    requestingTeamId = myMeetingTeam.id,
                    receivingTeamId = receivingMeetingTeam.id
                )
                meetingAttendanceRepositorySpy
                    .findAllByMeetingId(meetingId = meeting!!.id)
                    .size shouldBe 1
            }
        }
    }

})
