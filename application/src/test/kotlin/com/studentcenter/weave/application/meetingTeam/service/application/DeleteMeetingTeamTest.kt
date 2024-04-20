package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meeting.port.inbound.CancelAllMeeting
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.springframework.stereotype.Service

@Service("DeleteMeetingTeamTest")
class DeleteMeetingTeamTest : DescribeSpec({

    val meetingTeamRepository = MeetingTeamRepositorySpy()

    val cancelMeetingTeam = mockk<CancelAllMeeting>(relaxed = true)
    val sut = DeleteMeetingTeamService(
        meetingTeamRepository = meetingTeamRepository,
        cancelAllMeeting = cancelMeetingTeam,
    )

    val user = UserFixtureFactory.create()
    val userAuthentication = UserAuthenticationFixtureFactory.create(user)

    beforeEach {
        SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
    }

    afterEach {
        meetingTeamRepository.clear()
        SecurityContextHolder.clearContext()
        clearAllMocks()
    }

    describe("미팅 팀 삭제 유스케이스") {
        context("[실패] 현재 유저가 팀장이 아닌경우") {
            it("에러가 발생한다") {
                // arrange
                val leader = UserFixtureFactory.create()

                val currentUser = UserFixtureFactory.create()
                UserAuthenticationFixtureFactory
                    .create(currentUser)
                    .let { SecurityContextHolder.setContext(UserSecurityContext(it)) }


                val meetingTeam = MeetingTeamFixtureFactory.create(
                    leader = leader,
                    members = listOf(currentUser)
                ).also { meetingTeamRepository.save(it) }


                // act, assert
                shouldThrow<RuntimeException> {
                    sut.invoke(meetingTeam.id)
                }
            }
        }
        context("[성공] 현재 유저가 팀장인 경우") {
            it("해당 미팅팀을 삭제한다") {
                // arrange
                val currentUser = UserFixtureFactory.create()
                UserAuthenticationFixtureFactory
                    .create(currentUser)
                    .let { SecurityContextHolder.setContext(UserSecurityContext(it)) }


                val meetingTeam = MeetingTeamFixtureFactory.create(
                    leader = currentUser,
                ).also { meetingTeamRepository.save(it) }

                // act
                sut.invoke(meetingTeam.id)

                // assert
                meetingTeamRepository.findById(meetingTeam.id) shouldBe null
            }
        }
    }

})
