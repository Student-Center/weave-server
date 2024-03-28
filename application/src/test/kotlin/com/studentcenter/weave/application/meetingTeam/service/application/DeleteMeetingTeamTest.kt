package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meeting.port.inbound.CancelAllMeetingUseCase
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamMemberSummaryRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meetingTeam.service.domain.impl.MeetingTeamDomainServiceImpl
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
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
    val meetingMemberRepository = MeetingMemberRepositorySpy()
    val meetingTeamMemberSummaryRepository = MeetingTeamMemberSummaryRepositorySpy()
    val getUser = mockk<GetUser>()

    val meetingTeamDomainService = MeetingTeamDomainServiceImpl(
        meetingTeamRepository,
        meetingMemberRepository,
        meetingTeamMemberSummaryRepository,
        getUser,
    )
    val cancelMeetingTeamUseCase = mockk<CancelAllMeetingUseCase>(relaxed = true)
    val sut = DeleteMeetingTeamService(
        meetingTeamDomainService = meetingTeamDomainService,
        cancelAllMeetingUseCase = cancelMeetingTeamUseCase,
    )

    val user = UserFixtureFactory.create()
    val userAuthentication = UserAuthenticationFixtureFactory.create(user)

    beforeEach {
        SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
    }

    afterEach {
        meetingTeamRepository.clear()
        meetingMemberRepository.clear()
        SecurityContextHolder.clearContext()
        clearAllMocks()
    }

    describe("미팅 팀 삭제 유스케이스") {
        it("해당 미팀팀의 멤버가 아니라면 예외를 던진다.") {
            // arrange
            val meetingTeam = MeetingTeamFixtureFactory.create()
            val user1 = UserFixtureFactory.create()
            val user2 = UserFixtureFactory.create()

            val meetingMember1 =
                MeetingMember.create(meetingTeam.id, user1.id, MeetingMemberRole.LEADER)
            val meetingMember2 =
                MeetingMember.create(meetingTeam.id, user2.id, MeetingMemberRole.MEMBER)

            meetingTeamRepository.save(meetingTeam)
            meetingTeamRepository.putUserToTeamMember(user1.id, meetingTeam.id)
            meetingTeamRepository.putUserToTeamMember(user2.id, meetingTeam.id)
            meetingMemberRepository.save(meetingMember1)
            meetingMemberRepository.save(meetingMember2)

            // act, assert
            shouldThrow<RuntimeException> {
                sut.invoke(meetingTeam.id)
            }
        }

        it("해당 미팀팀과 미팅 멤버 정보를 삭제한다") {
            // arrange
            val meetingTeam = MeetingTeamFixtureFactory.create()
            val user2 = UserFixtureFactory.create()

            val meetingMember1 =
                MeetingMember.create(meetingTeam.id, user.id, MeetingMemberRole.LEADER)
            val meetingMember2 =
                MeetingMember.create(meetingTeam.id, user2.id, MeetingMemberRole.MEMBER)

            meetingTeamRepository.save(meetingTeam)
            meetingTeamRepository.putUserToTeamMember(user.id, meetingTeam.id)
            meetingTeamRepository.putUserToTeamMember(user2.id, meetingTeam.id)
            meetingMemberRepository.save(meetingMember1)
            meetingMemberRepository.save(meetingMember2)

            // act
            sut.invoke(meetingTeam.id)

            // assert
            meetingTeamRepository.findById(meetingTeam.id) shouldBe null
            meetingMemberRepository.findAllByMeetingTeamId(meetingTeam.id) shouldBe emptyList()
        }
    }

})
