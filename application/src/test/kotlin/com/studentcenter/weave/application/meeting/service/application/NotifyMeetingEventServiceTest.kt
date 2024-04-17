package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.meeting.outbound.MeetingEventMessagePortSpy
import com.studentcenter.weave.application.meeting.outbound.MeetingRepositorySpy
import com.studentcenter.weave.application.meeting.service.domain.impl.MeetingDomainServiceImpl
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.domain.meeting.entity.MeetingFixtureFactory
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import com.studentcenter.weave.domain.meeting.event.MeetingCompletedEvent
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummaryFixtureFactory
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

@DisplayName("NotifyMeetingEventService")
class NotifyMeetingEventServiceTest : DescribeSpec({

    val meetingRepository = MeetingRepositorySpy()
    val meetingDomainService = MeetingDomainServiceImpl(meetingRepository)
    val meetingEventMessagePort = MeetingEventMessagePortSpy()
    val getMeetingTeamMock = mockk<GetMeetingTeam>()

    val sut = NotifyMeetingEventService(
        meetingDomainService = meetingDomainService,
        meetingEventMessagePort = meetingEventMessagePort,
        getMeetingTeam = getMeetingTeamMock,
    )

    describe("notifyMeetingCompleted") {
        it("미팅 완료 이벤트 발생시 알림 메시지를 전송한다") {
            // arrange
            val requestMeetingTeam = MeetingTeamFixtureFactory.create()
            val receivingMeetingTeam = MeetingTeamFixtureFactory.create()

            val requestingMeetingTeamMemberSummary = MeetingTeamMemberSummaryFixtureFactory.create(
                meetingTeamId = requestMeetingTeam.id,
            )
            val receivingMeetingTeamMemberSummary = MeetingTeamMemberSummaryFixtureFactory.create(
                meetingTeamId = receivingMeetingTeam.id,
            )

            val meeting = MeetingFixtureFactory.create(
                requestingTeamId = requestMeetingTeam.id,
                receivingTeamId = receivingMeetingTeam.id,
                status = MeetingStatus.COMPLETED
            )
            meetingRepository.save(meeting)

            val event = MeetingCompletedEvent(meeting)

            every { getMeetingTeamMock.getMeetingTeamMemberSummaryByMeetingTeamId(requestMeetingTeam.id) } returns requestingMeetingTeamMemberSummary
            every {
                getMeetingTeamMock.getMeetingTeamMemberSummaryByMeetingTeamId(
                    receivingMeetingTeam.id
                )
            } returns receivingMeetingTeamMemberSummary
            every { getMeetingTeamMock.getById(requestMeetingTeam.id) } returns requestMeetingTeam
            every { getMeetingTeamMock.getById(receivingMeetingTeam.id) } returns receivingMeetingTeam

            // act
            sut.notifyMeetingCompleted(event)

            // assert
            meetingEventMessagePort.count() shouldBe 1
        }
    }

})
