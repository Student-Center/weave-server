package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.properties.MeetingTeamInvitationPropertiesFixtureFactory
import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamInvitationRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamMemberSummaryRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meetingTeam.util.impl.MeetingTeamInvitationServiceImpl
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks

@DisplayName("GetMeetingTeamByInvitationTest")
class GetMeetingTeamByInvitationTest : DescribeSpec({

    val meetingTeamInvitationProperties = MeetingTeamInvitationPropertiesFixtureFactory.create()
    val meetingTeamInvitationRepositorySpy = MeetingTeamInvitationRepositorySpy()

    val meetingTeamRepositorySpy = MeetingTeamRepositorySpy()
    val meetingTeamMemberSummaryRepositorySpy = MeetingTeamMemberSummaryRepositorySpy()

    val meetingTeamInvitationService = MeetingTeamInvitationServiceImpl(
        meetingTeamInvitationProperties = meetingTeamInvitationProperties,
        meetingTeamInvitationRepository = meetingTeamInvitationRepositorySpy,
    )

    val sut = GetMeetingTeamByInvitationCodeService(
        meetingTeamRepository = meetingTeamRepositorySpy,
        meetingTeamInvitationService = meetingTeamInvitationService,
    )

    afterEach {
        meetingTeamRepositorySpy.clear()
        meetingTeamMemberSummaryRepositorySpy.clear()
        meetingTeamInvitationRepositorySpy.clear()
        SecurityContextHolder.clearContext()
        clearAllMocks()
    }

    describe("초대 링크를 이용한 미팅팀 상세 조회") {
        context("초대 링크가 유효하지 않은 경우") {
            it("에러가 발생한다.") {
                // arrange
                val meetingTeam = MeetingTeamFixtureFactory.create()

                UserFixtureFactory.create()
                    .let { UserAuthenticationFixtureFactory.create(it) }
                    .let { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val meetingTeamInvitation = meetingTeamInvitationService.create(meetingTeam.id)

                // act & assert
                shouldThrow<NoSuchElementException> {
                    sut.invoke(meetingTeamInvitation.invitationCode)
                }

            }
        }

        context("초대 링크가 유효한 경우") {
            it("미팅팀 상세 조회가 정상적으로 이루어진다.") {
                // arrange
                val meetingTeam = MeetingTeamFixtureFactory.create()
                    .also { meetingTeamRepositorySpy.save(it) }

                UserFixtureFactory.create()
                    .let { UserAuthenticationFixtureFactory.create(it) }
                    .let { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val meetingTeamInvitation = meetingTeamInvitationService.create(meetingTeam.id)

                // act
                val result = sut.invoke(meetingTeamInvitation.invitationCode)

                // assert
                result shouldBe meetingTeam
            }
        }
    }

})
