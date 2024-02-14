package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.meeting.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meeting.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamDeleteUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meeting.service.domain.impl.MeetingTeamDomainServiceImpl
import com.studentcenter.weave.domain.meeting.entity.MeetingMember
import com.studentcenter.weave.domain.meeting.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meeting.enums.MeetingMemberRole
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.stereotype.Service

@Service("meetingTeamDeleteApplicationService")
class MeetingTeamDeleteApplicationServiceTest : DescribeSpec({

    val meetingTeamRepository = MeetingTeamRepositorySpy()
    val meetingMemberRepository = MeetingMemberRepositorySpy()
    val meetingTeamDomainService = MeetingTeamDomainServiceImpl(
        meetingTeamRepository,
        meetingMemberRepository,
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
