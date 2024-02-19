package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.meetingTeam.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamMemberSummaryRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamDeleteUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.impl.MeetingTeamDomainServiceImpl
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.springframework.stereotype.Service

@Service("meetingTeamDeleteApplicationService")
class MeetingTeamDeleteApplicationServiceTest : DescribeSpec({

    val meetingTeamRepository = MeetingTeamRepositorySpy()
    val meetingMemberRepository = MeetingMemberRepositorySpy()
    val meetingTeamMemberSummaryRepository = MeetingTeamMemberSummaryRepositorySpy()
    val userQueryUseCase = mockk<UserQueryUseCase>()

    val meetingTeamDomainService = MeetingTeamDomainServiceImpl(
        meetingTeamRepository,
        meetingMemberRepository,
        meetingTeamMemberSummaryRepository,
        userQueryUseCase,
    )
    val sut = MeetingTeamDeleteApplicationService(meetingTeamDomainService)

    describe("미팅 팀 삭제 유스케이스") {
        it("해당 미팀팀과 미팅 멤버 정보를 삭제한다") {
            // arrange
            val meetingTeam = MeetingTeamFixtureFactory.create()
            val user1 = UserFixtureFactory.create()
            val user2 = UserFixtureFactory.create()

            val meetingMember1 =
                MeetingMember.create(meetingTeam.id, user1.id, MeetingMemberRole.LEADER)
            val meetingMember2 =
                MeetingMember.create(meetingTeam.id, user2.id, MeetingMemberRole.MEMBER)

            meetingTeamRepository.save(meetingTeam)
            meetingMemberRepository.save(meetingMember1)
            meetingMemberRepository.save(meetingMember2)

            // act
            sut.invoke(MeetingTeamDeleteUseCase.Command(meetingTeam.id))

            // assert
            meetingTeamRepository.findById(meetingTeam.id) shouldBe null
            meetingMemberRepository.findAllByMeetingTeamId(meetingTeam.id) shouldBe emptyList()
        }
    }

})
