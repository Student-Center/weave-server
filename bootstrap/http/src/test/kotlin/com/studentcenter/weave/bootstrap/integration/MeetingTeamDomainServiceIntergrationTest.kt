package com.studentcenter.weave.bootstrap.integration

import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.user.port.outbound.UserRepository
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.common.exception.CustomException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@DisplayName("MeetingTeamDomainService 통합 테스트")
class MeetingTeamDomainServiceIntegrationTest(
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val userRepository: UserRepository,
) : IntegrationTestDescribeSpec({

    describe("미팅 팀 입장 요청 동시성 테스트") {
        context("잔여 티오가 하나인 미팅팀에 두 유저가 동시에 입장 요청을 하는 경우") {
            it("에러가 발생한다.") {
                // arrange
                val memberCount = 2
                val leaderUser = UserFixtureFactory.create()
                val user1 = UserFixtureFactory.create()
                val user2 = UserFixtureFactory.create()

                userRepository.save(leaderUser)
                userRepository.save(user1)
                userRepository.save(user2)

                val meetingTeam = MeetingTeamFixtureFactory.create(memberCount = memberCount)
                meetingTeamDomainService.save(meetingTeam)

                meetingTeamDomainService.addMember(
                    user = leaderUser,
                    meetingTeam = meetingTeam,
                    role = MeetingMemberRole.LEADER
                )

                // act
                shouldThrow<CustomException> {
                    runBlocking {
                        launch(Dispatchers.Default) {
                            meetingTeamDomainService.addMember(
                                user = user1,
                                meetingTeam = meetingTeam,
                                role = MeetingMemberRole.MEMBER
                            )
                        }
                        launch(Dispatchers.Default) {
                            meetingTeamDomainService.addMember(
                                user = user2,
                                meetingTeam = meetingTeam,
                                role = MeetingMemberRole.MEMBER
                            )
                        }
                    }
                }

                meetingTeamDomainService.countByMeetingTeamId(meetingTeam.id) shouldBe 2
            }
        }
    }

})
