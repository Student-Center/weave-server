package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meeting.outbound.MeetingRepositorySpy
import com.studentcenter.weave.application.meeting.service.domain.impl.MeetingDomainServiceImpl
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meeting.entity.MeetingFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meetingTeam.exception.MeetingTeamException
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import java.util.*

@DisplayName("GetMeetingAttendancesServiceTest")
class FindMyRequestMeetingByReceivingTeamIdServiceTest : DescribeSpec({

    val meetingRepositorySpy = MeetingRepositorySpy()
    val meetingDomainService = MeetingDomainServiceImpl(
        meetingRepository = meetingRepositorySpy,
    )
    val getMeetingTeam = mockk<GetMeetingTeam>()
    val sut = FindMyRequestMeetingByReceivingTeamIdService(
        meetingDomainService = meetingDomainService,
        getMeetingTeam = getMeetingTeam,
    )

    val user = UserFixtureFactory.create()
    val userAuthentication = UserAuthenticationFixtureFactory.create(user)

    beforeEach {
        SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
    }

    afterEach {
        meetingRepositorySpy.clear()
        SecurityContextHolder.clearContext()
        clearAllMocks()
    }

    describe("미팅 요청 여부 조회 테스트") {
        context("내가 속한 팀이 없는 경우") {
            it("예외를 던진다") {
                // arrange
                every { getMeetingTeam.findByMemberUserId(user.id) } returns null

                // act, assert
                shouldThrow<MeetingTeamException.CanNotFindMyMeetingTeam> { sut.invoke(UUID.randomUUID()) }
            }
        }

        context("요청한 미팅이 없는 경우") {
            it("참여 정보가 조회된다") {
                // arrange
                val myTeam = MeetingTeamFixtureFactory.create(status = MeetingTeamStatus.PUBLISHED)
                every { getMeetingTeam.findByMemberUserId(user.id) } returns myTeam

                // act
                val response = sut.invoke(UUID.randomUUID())

                // assert
                response shouldBe null
            }
        }

        context("요청한 미팅이 있는 경우") {
            it("참여 정보가 조회된다") {
                // arrange
                val myTeam = MeetingTeamFixtureFactory.create(
                    status = MeetingTeamStatus.PUBLISHED,
                    gender = Gender.MAN,
                )
                val receivingTeam = MeetingTeamFixtureFactory.create(
                    status = MeetingTeamStatus.PUBLISHED,
                    gender = Gender.WOMAN,
                )
                meetingRepositorySpy.save(
                    MeetingFixtureFactory.create(
                        requestingTeamId = myTeam.id,
                        receivingTeamId = receivingTeam.id,
                    )
                )
                every { getMeetingTeam.findByMemberUserId(user.id) } returns myTeam

                // act
                val response = sut.invoke(receivingTeam.id)

                // assert
                response shouldNotBe null
            }
        }
    }

})
