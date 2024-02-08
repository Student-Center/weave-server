package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meeting.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamCreateUseCase
import com.studentcenter.weave.application.meeting.service.domain.impl.MeetingTeamDomainServiceImpl
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.service.domain.impl.UserDomainServiceImpl
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe

@DisplayName("MeetingTeamCreateApplicationServiceTest")
class MeetingTeamCreateApplicationServiceTest : DescribeSpec({

    val meetingTeamRepositorySpy = MeetingTeamRepositorySpy()
    val meetingTeamDomainService = MeetingTeamDomainServiceImpl(meetingTeamRepositorySpy)
    val userRepositorySpy = UserRepositorySpy()
    val userDomainService = UserDomainServiceImpl(userRepositorySpy)
    val sut = MeetingTeamCreateApplicationService(
        meetingTeamDomainService,
        userDomainService,
    )

    afterTest {
        SecurityContextHolder.clearContext()
        meetingTeamRepositorySpy.clear()
        userRepositorySpy.clear()
    }

    describe("미팅 팀 생성 유스케이스") {
        context("사용자가 로그인 한 상태이면") {
            it("해당 사용자가 팀장인 미팅 팀을 생성한다") {
                // arrange
                val teamIntroduce = TeamIntroduce("팀 소개")
                val memberCount = 3
                val location = Location.BUSAN
                val command = MeetingTeamCreateUseCase.Command(
                    teamIntroduce = teamIntroduce,
                    memberCount = memberCount,
                    location = location
                )

                val leaderUserFixture: User = UserFixtureFactory.create()
                userRepositorySpy.save(leaderUserFixture)
                UserAuthentication(
                    userId = leaderUserFixture.id,
                    email = leaderUserFixture.email,
                    nickname = leaderUserFixture.nickname,
                    avatar = leaderUserFixture.avatar
                ).let { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                // act
                sut.invoke(command)

                // assert
                meetingTeamRepositorySpy.findByLeaderUserId(leaderUserFixture.id) shouldNotBe null
            }
        }
    }

})
